package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Set<Comment> findCommentsByTicketId(int id);
}
