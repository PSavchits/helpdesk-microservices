package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.CommentOverviewDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.exception.TicketNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.mapper.CommentMapper;
import innowise.microservice.helpdesk.ticketsservice.repository.CommentRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TicketRepository ticketRepository;

    public Set<Comment> getCommentsByTicket(int ticketId) {
        return commentRepository.findCommentsByTicketId(ticketId);
    }

    public Set<CommentOverviewDTO> getCommentsByTicketId(int ticketId) {
        return commentRepository.findCommentsByTicketId(ticketId)
                .stream().map(commentMapper::commentToCommentDto)
                .collect(Collectors.toSet());
    }

    public void addComment(CommentDTO commentDTO, User creator) {
        Ticket ticket = ticketRepository.findTicketById(commentDTO.getTicketId()).orElseThrow(TicketNotFoundException::new);

        commentRepository.save(commentMapper.commentDtoToComment(commentDTO, creator, ticket));
    }

    public Comment createComment(User user, TicketDTO ticketDTO, Ticket ticket) {
        return commentMapper.ticketDtoToComment(ticketDTO, user, ticket);
    }

    public List<Comment> attachComments(TicketDTO ticketDTO, User user, Ticket ticket) {
        List<Comment> comments = new ArrayList<>();

        if (Boolean.TRUE.equals(commentExists(ticketDTO))) {
            Comment comment = createComment(user, ticketDTO, ticket);
            comments.add(comment);
        }

        return comments;
    }

    public Boolean commentExists(TicketDTO ticketDTO) {
        return (ticketDTO.getCommentText() != null && !ticketDTO.getCommentText().isEmpty());
    }
}
