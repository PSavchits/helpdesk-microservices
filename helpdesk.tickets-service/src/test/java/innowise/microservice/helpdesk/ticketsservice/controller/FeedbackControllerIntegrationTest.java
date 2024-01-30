package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.FeedbackTestHelper;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.UserRepository;
import innowise.microservice.helpdesk.ticketsservice.services.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper.user;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FeedbackControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user = TicketServiceTestHelper.createUser();
        userRepository.save(user);

        Ticket testTicket = TicketServiceTestHelper.createTicket();
        ticketRepository.save(testTicket);

    }

    @Test
    void createFeedback_shouldReturnOk_whenValidFeedbackDTOProvided() {
        FeedbackDTO feedbackDTO = FeedbackTestHelper.createFeedbackDTO();

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/helpdesk-service/feedback",
                new HttpEntity<>(feedbackDTO),
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Feedback created successfully");
    }

    @Test
    void getFeedback_shouldReturnFeedback_whenFeedbackExists() {
        FeedbackDTO feedbackDTO = FeedbackTestHelper.createFeedbackDTO();
        feedbackDTO.setTicketId(1);
        feedbackService.createFeedback(feedbackDTO, user);

        ResponseEntity<Feedback> response = restTemplate.exchange(
                "http://localhost:" + port + "/helpdesk-service/feedback/" + 1,
                HttpMethod.GET,
                null,
                Feedback.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getText()).isEqualTo("test text");
    }
}
