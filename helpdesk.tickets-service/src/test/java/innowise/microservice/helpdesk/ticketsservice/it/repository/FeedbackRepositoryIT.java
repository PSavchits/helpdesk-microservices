package innowise.microservice.helpdesk.ticketsservice.it.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static innowise.microservice.helpdesk.ticketsservice.it.helpers.EntityFactory.EXPECTED_FEEDBACK;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedbackRepositoryIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void findFeedbackById_shouldPass_WhenUserIsExists() {

        feedbackRepository.save(EXPECTED_FEEDBACK);

        Optional<Feedback> foundFeedback = feedbackRepository.findFeedbackById(EXPECTED_FEEDBACK.getId());

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getRate()).isEqualTo(EXPECTED_FEEDBACK.getRate());
        assertThat(foundFeedback.get().getText()).isEqualTo(EXPECTED_FEEDBACK.getText());
    }

    @Test
    void findFeedbackByTicketId_shouldPass_WhenUserIsExists() {

        feedbackRepository.save(EXPECTED_FEEDBACK);

        Optional<Feedback> foundFeedback = feedbackRepository.findFeedbackByTicketId(EXPECTED_FEEDBACK.getTicket().getId());

        assertThat(foundFeedback).isPresent();
        assertThat(foundFeedback.get().getRate()).isEqualTo(EXPECTED_FEEDBACK.getRate());
        assertThat(foundFeedback.get().getText()).isEqualTo(EXPECTED_FEEDBACK.getText());
    }
}
