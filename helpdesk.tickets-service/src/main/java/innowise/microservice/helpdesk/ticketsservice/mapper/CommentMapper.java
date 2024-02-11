package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class CommentMapper {
    public Comment ticketDtoToComment(TicketDTO ticketDTO, User creator, Ticket ticket) {
        return Comment.builder()
                .user(creator)
                .text(ticketDTO.getCommentText())
                .date(LocalDate.now())
                .ticket(ticket)
                .build();
    }

    public Comment commentDtoToComment(CommentDTO commentDTO, User creator, Ticket ticket) {
        return Comment.builder()
                .user(creator)
                .text(commentDTO.getText())
                .date(LocalDate.now())
                .ticket(ticket)
                .build();
    }
}