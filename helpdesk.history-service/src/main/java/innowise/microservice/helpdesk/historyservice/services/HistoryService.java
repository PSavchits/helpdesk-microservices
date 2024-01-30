package innowise.microservice.helpdesk.historyservice.services;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.exception.HistoryNotFoundException;
import innowise.microservice.helpdesk.historyservice.mapper.HistoryMapper;
import innowise.microservice.helpdesk.historyservice.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    public Optional<History> getHistory(int historyId) {
        return Optional.ofNullable(historyRepository.findById(historyId)
                .orElseThrow(() -> new HistoryNotFoundException(historyId)));
    }

    public List<History> getHistoriesByTicketId(int ticketId) {
        return historyRepository.findHistoriesByTicketId(ticketId);
    }

    public void createHistory(HistoryDTO historyDTO) {
        History history = historyMapper.toHistory(historyDTO);
        historyRepository.insert(history);
    }
}
