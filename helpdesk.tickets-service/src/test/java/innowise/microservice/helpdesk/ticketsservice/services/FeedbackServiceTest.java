package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.FeedbackTestHelper;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.mapper.FeedbackMapper;
import innowise.microservice.helpdesk.ticketsservice.repository.FeedbackRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {
    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    void getFeedbackById_shouldReturnFeedback_whenFeedbackExists() {
        int feedbackId = 1;
        Feedback expectedFeedback = FeedbackTestHelper.createFeedbackWithId(1L);
        Optional<Feedback> expectedOptionalFeedback = Optional.of(expectedFeedback);

        when(feedbackRepository.findFeedbackById(feedbackId)).thenReturn(expectedOptionalFeedback);

        Optional<Feedback> actualFeedback = feedbackService.getFeedbackById(feedbackId);

        assertTrue(actualFeedback.isPresent());
        assertEquals(expectedFeedback, actualFeedback.get());
        verify(feedbackRepository).findFeedbackById(feedbackId);
    }

    @Test
    void getFeedbackByTicketId_shouldReturnFeedback_whenFeedbackExists() {
        int ticketId = 1;
        Feedback expectedFeedback = FeedbackTestHelper.createFeedbackWithId(1L);
        Optional<Feedback> expectedOptionalFeedback = Optional.of(expectedFeedback);

        when(feedbackRepository.findFeedbackByTicketId(ticketId)).thenReturn(expectedOptionalFeedback);

        Optional<Feedback> actualFeedback = feedbackService.getFeedbackByTicketId(ticketId);

        assertTrue(actualFeedback.isPresent());
        assertEquals(expectedFeedback, actualFeedback.get());
        verify(feedbackRepository).findFeedbackByTicketId(ticketId);
    }

    @Test
    void createFeedback_shouldCreateFeedbackAndSendEmail_whenTicketExists() {
        int ticketId = 1;

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setTicketId(ticketId);

        User creator = TicketServiceTestHelper.createUser();

        Ticket existingTicket = new Ticket();
        existingTicket.setId(ticketId);
        existingTicket.setOwner(creator);

        Feedback feedback = new Feedback();

        when(ticketRepository.findTicketById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(feedbackMapper.feedbackDTOtoFeedback(feedbackDTO, existingTicket, creator)).thenReturn(feedback);

        feedbackService.createFeedback(feedbackDTO, creator);

        verify(ticketRepository).findTicketById(ticketId);
        verify(feedbackMapper).feedbackDTOtoFeedback(feedbackDTO, existingTicket, creator);
        verify(emailService).sendFeedbackEmail(creator.getEmail(), ticketId);
        verify(feedbackRepository).save(feedback);
    }
}
