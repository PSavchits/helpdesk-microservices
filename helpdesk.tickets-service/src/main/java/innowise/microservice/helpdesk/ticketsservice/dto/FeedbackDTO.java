package innowise.microservice.helpdesk.ticketsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

    private int rate;
    private String text;
    private int ticketId;
}
