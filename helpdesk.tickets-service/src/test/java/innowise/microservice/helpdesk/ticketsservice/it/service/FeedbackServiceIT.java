package innowise.microservice.helpdesk.ticketsservice.it.service;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.mapper.FeedbackMapper;
import innowise.microservice.helpdesk.ticketsservice.repository.FeedbackRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.FeedbackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.EXPECTED_FEEDBACK;
import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.ticket;
import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedbackServiceIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Autowired
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    void getFeedbackById_shouldPass_WhenTicketExists() {
        when(feedbackRepository.findFeedbackById(1)).thenReturn(Optional.of(EXPECTED_FEEDBACK));

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackById(1);

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getId()).isEqualTo(1L);
    }

    @Test
    void getFeedbackByTicketId_shouldPass_WhenTicketExists() {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        when(feedbackRepository.findFeedbackByTicketId(1)).thenReturn(Optional.of(feedback));

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackByTicketId(1);

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getId()).isEqualTo(1L);
    }

    @Test
    void createFeedback_shouldCreateFeedback() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setTicketId(1);

        when(ticketRepository.findTicketById(1)).thenReturn(Optional.of(ticket));
        when(feedbackMapper.feedbackDTOtoFeedback(feedbackDTO, ticket, user)).thenReturn(EXPECTED_FEEDBACK);

        feedbackService.createFeedback(feedbackDTO, user);

        assertNotNull(EXPECTED_FEEDBACK.getId());
        Assertions.assertEquals("Great service", EXPECTED_FEEDBACK.getText());
    }
}

