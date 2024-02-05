package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "ticketDTO.name")
    @Mapping(target = "description", source = "ticketDTO.description")
    @Mapping(target = "createdOn", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "desiredResolutionDate", source = "ticketDTO.desiredResolutionDate")
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "owner", source = "creator")
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "attachments", expression = "java(new java.util.HashSet<>())")
    @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())")
    Ticket ticketDtoToTicket(TicketDTO ticketDTO, User creator, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "ticketDTO.name")
    @Mapping(target = "description", source = "ticketDTO.description")
    @Mapping(target = "desiredResolutionDate", source = "ticketDTO.desiredResolutionDate")
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "approver", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "urgency", source = "ticketDTO.urgency")
    @Mapping(target = "attachments", expression = "java(new java.util.HashSet<>())")
    @Mapping(target = "comments", expression = "java(new java.util.HashSet<>())")
    void updateTicketFromDto(TicketDTO ticketDTO, @MappingTarget Ticket ticket, Category category);
}

