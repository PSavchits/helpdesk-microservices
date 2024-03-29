package innowise.microservice.helpdesk.historyservice.repository;

import innowise.microservice.helpdesk.historyservice.entity.History;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends MongoRepository<History, String> {
    List<History> findHistoriesByTicketId(int ticketId);

    Optional<History> findById(int id);
}
