package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "creator")
    @Mapping(target = "text", source = "ticketDTO.commentText")
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticket", source = "ticket")
    Comment ticketDtoToComment(TicketDTO ticketDTO, User creator, Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "creator")
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticket", source = "ticket")
    Comment commentDtoToComment(CommentDTO commentDTO, User creator, Ticket ticket);
}
