package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "creator")
    @Mapping(target = "text", source = "feedbackDTO.text")
    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "ticket", source = "ticket")
    @Mapping(target = "rate", source = "feedbackDTO.rate")
    Feedback feedbackDTOtoFeedback(FeedbackDTO feedbackDTO, Ticket ticket, User creator);
}
