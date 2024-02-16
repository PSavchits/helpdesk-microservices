package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.CommentOverviewDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/helpdesk-service/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO, @AuthenticationPrincipal User creator) {
        commentService.addComment(commentDTO, creator);
        return ResponseEntity.ok("Comment created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Set<CommentOverviewDTO>> getComments(@PathVariable("id") int id) {
        return new ResponseEntity<>(commentService.getCommentsByTicketId(id), HttpStatus.OK);
    }
}
