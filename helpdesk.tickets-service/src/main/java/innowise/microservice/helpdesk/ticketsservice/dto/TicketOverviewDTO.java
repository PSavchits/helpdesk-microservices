package innowise.microservice.helpdesk.ticketsservice.dto;

import innowise.microservice.helpdesk.ticketsservice.enums.State;
import innowise.microservice.helpdesk.ticketsservice.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketOverviewDTO {

    private Integer id;
    private String name;
    private String category;
    private State state;
    private Urgency urgency;
    private String owner;
    private String approver;
    private String assignee;
    private String description;
    private LocalDate desiredResolutionDate;
}
