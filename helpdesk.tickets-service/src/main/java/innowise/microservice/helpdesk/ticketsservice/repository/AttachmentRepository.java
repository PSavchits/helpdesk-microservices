package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Optional<Attachment> findById(int id);
    Set<Attachment> findByTicketId(int id);
}
