package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.services.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/helpdesk-service/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable int id) {
        return attachmentService.downloadAttachment(id);
    }
}
