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
public class TicketReadDTO {

    Integer id;
    String name;
    LocalDate desiredResolutionDate;
    Urgency urgency;
    State state;
}
