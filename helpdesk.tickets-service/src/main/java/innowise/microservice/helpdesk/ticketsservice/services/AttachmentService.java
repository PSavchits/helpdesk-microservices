package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.AttachmentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.exception.AttachmentNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.mapper.AttachmentMapper;
import innowise.microservice.helpdesk.ticketsservice.mapper.HistoryMapper;
import innowise.microservice.helpdesk.ticketsservice.mq.MessageSender;
import innowise.microservice.helpdesk.ticketsservice.repository.AttachmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.ATTACH_FILE_ACTION;

@Service
@AllArgsConstructor
@Slf4j
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final MessageSender messageSender;
    private final HistoryMapper historyMapper;
    private final AttachmentMapper attachmentMapper;

    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "doc", "docx", "png", "jpeg", "jpg");
    private static final Long MAX_FILE_SIZE = 5242880L;

    public Set<AttachmentDTO> getAttachmentsByTicketId(int id) {
        return attachmentRepository.findByTicketId(id)
                .stream().map(attachmentMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    public Optional<Attachment> getAttachmentById(int attachmentId) {
        return attachmentRepository.findById(attachmentId);
    }

    public List<Attachment> attachFiles(List<MultipartFile> files, Ticket ticket, User editor) {
        List<Attachment> attachments = new ArrayList<>();
        if (Boolean.TRUE.equals(filesExists(files))) {
            try {
                for (MultipartFile file : files) {
                    validateAttachment(file);

                    Attachment attachment = new Attachment();
                    attachment.setTicket(ticket);
                    attachment.setName(file.getOriginalFilename());
                    attachment.setBlob(file.getBytes());

                    String description = "File is attached: " + file.getOriginalFilename();
                    HistoryDTO historyDTO = historyMapper.toHistoryDTO(editor.getId(), ticket.getId(), ATTACH_FILE_ACTION, description);
                    messageSender.sendMessage(historyDTO);

                    attachments.add(attachment);
                }
            } catch (IOException e) {
                log.error("Attachment error", e);
            }
        }
        return attachments;
    }

    public void validateAttachment(MultipartFile file) {

        if (!ALLOWED_EXTENSIONS.contains(getFileExtension(Objects.requireNonNull(file.getOriginalFilename())))) {
            throw new RuntimeException("The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("The size of attached file should not be greater than 5 Mb. Please select another file.");
        }
    }

    public String getFileExtension(String fileName) {
        return (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
                ? fileName.substring(fileName.lastIndexOf(".") + 1)
                : "";
    }

    public Boolean filesExists(List<MultipartFile> files) {
        return (files != null && files.stream().anyMatch(file -> file.getSize() > 0));
    }

    public ResponseEntity<Resource> downloadAttachment(int attachmentId) {
        Attachment attachment = getAttachmentById(attachmentId).orElseThrow(AttachmentNotFoundException::new);

        ByteArrayResource resource = new ByteArrayResource(attachment.getBlob());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(attachment.getBlob().length)
                .body(resource);
    }
}
