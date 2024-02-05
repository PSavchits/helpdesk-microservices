package innowise.microservice.helpdesk.ticketsservice.dto;

import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import innowise.microservice.helpdesk.ticketsservice.enums.Urgency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Integer id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate desiredResolutionDate;
    private User assignee;
    private User owner;
    private State state;
    private String category;
    private Urgency urgency;
    private User approver;
    private Set<MultipartFile> attachments;
    private String commentText;
}
