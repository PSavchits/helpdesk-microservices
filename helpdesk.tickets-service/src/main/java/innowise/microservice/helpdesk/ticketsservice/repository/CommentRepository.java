package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByTicketId(int id);
}
