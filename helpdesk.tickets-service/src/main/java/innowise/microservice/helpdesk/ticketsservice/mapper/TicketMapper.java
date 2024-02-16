package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketOverviewDTO;
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
                .state(ticketDTO.getState())
                .urgency(ticketDTO.getUrgency())
                .desiredResolutionDate(ticketDTO.getDesiredResolutionDate())
                .owner(creator)
                .category(category)
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .build();
    }

    public Ticket updateTicketFromDto(TicketDTO ticketDTO, Category category, Ticket ticket) {
        return Ticket.builder()
                .id(ticket.getId())
                .name(ticketDTO.getName())
                .description(ticketDTO.getDescription())
                .createdOn(ticket.getCreatedOn())
                .owner(ticket.getOwner())
                .desiredResolutionDate(ticketDTO.getDesiredResolutionDate())
                .category(category)
                .urgency(ticketDTO.getUrgency())
                .state(ticketDTO.getState())
                .category(category)
                .attachments(new HashSet<>())
                .comments(new HashSet<>())
                .build();
    }

    public TicketOverviewDTO ticketToTicketDto(Ticket ticket) {
        return TicketOverviewDTO.builder()
                .id(ticket.getId())
                .name(ticket.getName())
                .desiredResolutionDate(ticket.getDesiredResolutionDate())
                .category(ticket.getCategory().getName())
                .state(ticket.getState())
                .urgency(ticket.getUrgency())
                .owner(ticket.getOwner().getFirstname())
                .approver(ticket.getApprover() != null ? ticket.getApprover().getFirstname() : "")
                .assignee(ticket.getAssignee() != null ? ticket.getAssignee().getFirstname() : "")
                .description(ticket.getDescription() != null ? ticket.getDescription() : "")
                .build();
    }
}
