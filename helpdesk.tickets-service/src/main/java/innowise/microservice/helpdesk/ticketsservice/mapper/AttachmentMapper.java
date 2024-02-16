package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.AttachmentDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentDTO mapToDto(Attachment attachment) {
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .blob(attachment.getBlob())
                .build();
    }
}
