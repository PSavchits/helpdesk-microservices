package innowise.microservice.helpdesk.historyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

    private LocalDate created;
    private Integer ticketId;
    private Integer userId;
    private String action;
    private String description;
}
