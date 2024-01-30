package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.exception.FeedbackNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.exception.TicketNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.mapper.FeedbackMapper;
import innowise.microservice.helpdesk.ticketsservice.repository.FeedbackRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final EmailService emailService;
    private final TicketRepository ticketRepository;

    public Optional<Feedback> getFeedbackById(int id) {
        return Optional.ofNullable(feedbackRepository.findFeedbackById(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id)));
    }

    public Optional<Feedback> getFeedbackByTicketId(int id) {
        return Optional.ofNullable(feedbackRepository.findFeedbackByTicketId(id)
                .orElseThrow(() -> new FeedbackNotFoundException(id)));
    }

    public void createFeedback(FeedbackDTO feedbackDTO, User creator) {
        Ticket existingTicket = ticketRepository.findTicketById(feedbackDTO.getTicketId())
                .orElseThrow(TicketNotFoundException::new);
        Feedback feedback = feedbackMapper.feedbackDTOtoFeedback(feedbackDTO, existingTicket, creator);

        emailService.sendFeedbackEmail(existingTicket.getOwner().getEmail(), feedbackDTO.getTicketId());

        feedbackRepository.save(feedback);
    }
}
