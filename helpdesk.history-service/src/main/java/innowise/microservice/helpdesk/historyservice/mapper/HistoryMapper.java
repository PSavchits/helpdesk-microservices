package innowise.microservice.helpdesk.historyservice.mapper;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.entity.History;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HistoryMapper {
    public History toHistory(HistoryDTO historyDTO) {
        return History.builder()
                .created(LocalDate.now())
                .action(historyDTO.getAction())
                .ticketId(historyDTO.getTicketId())
                .userId(historyDTO.getUserId())
                .description(historyDTO.getAction())
                .build();
    }
}
