package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HistoryMapper {

    public HistoryDTO toHistoryDTO(Integer userId, Integer ticketId, String action) {
        return HistoryDTO.builder()
                .created(LocalDate.now())
                .action(action)
                .ticketId(ticketId)
                .userId(userId)
                .description(action)
                .build();
    }

    public HistoryDTO toHistoryDTO(Integer userId, Integer ticketId, String action, String description) {
        return HistoryDTO.builder()
                .created(LocalDate.now())
                .action(action)
                .ticketId(ticketId)
                .userId(userId)
                .description(description)
                .build();
    }
}
