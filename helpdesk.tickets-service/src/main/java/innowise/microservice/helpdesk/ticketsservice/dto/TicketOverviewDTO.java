package innowise.microservice.helpdesk.ticketsservice.dto;

import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketOverviewDTO {

    private Feedback feedback;
    private User currentUser;
    private Ticket ticket;
    private Category category;
    private List<Attachment> attachments;
    private List<Comment> comments;
}
