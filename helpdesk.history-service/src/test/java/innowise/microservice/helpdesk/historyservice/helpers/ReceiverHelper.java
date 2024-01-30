package innowise.microservice.helpdesk.historyservice.helpers;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;

import java.time.LocalDate;

public class ReceiverHelper {

    public static HistoryDTO createHistoryDTO() {
        return HistoryDTO.builder()
                .ticketId(1)
                .created(LocalDate.of(2025,5,5))
                .action("Test Action")
                .description("Test Action")
                .build();
    }
}
