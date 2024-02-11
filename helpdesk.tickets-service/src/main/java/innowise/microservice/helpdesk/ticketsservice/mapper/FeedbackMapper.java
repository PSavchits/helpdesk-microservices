package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FeedbackMapper {
    public Feedback feedbackDTOtoFeedback(FeedbackDTO feedbackDTO, Ticket ticket, User creator) {
        return Feedback.builder()
                .user(creator)
                .text(feedbackDTO.getText())
                .date(LocalDate.now())
                .ticket(ticket)
                .rate(feedbackDTO.getRate())
                .build();
    }
}
