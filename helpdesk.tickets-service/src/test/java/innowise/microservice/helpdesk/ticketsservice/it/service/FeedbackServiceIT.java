package innowise.microservice.helpdesk.ticketsservice.it.service;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.repository.FeedbackRepository;
import innowise.microservice.helpdesk.ticketsservice.services.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.EXPECTED_FEEDBACK;
import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.user;
import static org.assertj.core.api.Assertions.assertThat;

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
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired

    @Test
    void getFeedbackById_shouldPass_WhenFeedbackExists() {
        feedbackRepository.save(EXPECTED_FEEDBACK);

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackById(1L);

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getId()).isEqualTo(1L);
    }

    @Test
    void getFeedbackByTicketId_shouldPass_WhenTicketExists() {
        feedbackRepository.save(EXPECTED_FEEDBACK);

        Optional<Feedback> foundFeedback = feedbackService.getFeedbackByTicketId(1);

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getId()).isEqualTo(1L);
    }

    @Test
    void createFeedback_shouldCreateFeedback() {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setTicketId(1);
        feedbackDTO.setRate(5);
        feedbackDTO.setText("Great service");

        feedbackService.createFeedback(feedbackDTO, user);

        Optional<Feedback> createdFeedback = feedbackRepository.findFeedbackById(1L);

        assertThat(createdFeedback).isPresent();
        assertThat(createdFeedback.get().getText()).isEqualTo("Great service");
        assertThat(createdFeedback.get().getRate()).isEqualTo(5);
    }
}

