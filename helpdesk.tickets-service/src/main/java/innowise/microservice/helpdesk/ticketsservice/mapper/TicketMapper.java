package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class TicketMapper {

    public Ticket ticketDtoToTicket(TicketDTO ticketDTO, User creator, Category category) {
        return Ticket.builder()
                .name(ticketDTO.getName())
                .description(ticketDTO.getDescription())
                .createdOn(LocalDate.now())
                .desiredResolutionDate(ticketDTO.getDesiredResolutionDate())
                .owner(creator)
                .category(category)
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .build();
    }

    public Ticket updateTicketFromDto(TicketDTO ticketDTO, Category category) {
        return Ticket.builder()
                .name(ticketDTO.getName())
                .description(ticketDTO.getDescription())
                .desiredResolutionDate(ticketDTO.getDesiredResolutionDate())
                .category(category)
                .urgency(ticketDTO.getUrgency())
                .category(category)
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .build();
    }
}
