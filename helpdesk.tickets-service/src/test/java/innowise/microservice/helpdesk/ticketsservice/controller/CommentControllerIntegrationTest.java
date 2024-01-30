package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.MapperHelper;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CommentControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setup() {
        Ticket testTicket = TicketServiceTestHelper.createTicket();
        ticketRepository.save(testTicket);

        User user = TicketServiceTestHelper.createUser();
        userRepository.save(user);

    }

    @Test
    void addComment_shouldCreateComment() {
        CommentDTO commentDTO = MapperHelper.createCommentDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(String.valueOf(1), "");

        HttpEntity<CommentDTO> request = new HttpEntity<>(commentDTO, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/helpdesk-service/comments",
                request,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("Comment created successfully", responseEntity.getBody());
    }

    @Test
    void addComment_shouldReturnNotFound_whenInvalidCredentials() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Test comment");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("testUser").password("password").roles("MANAGER").build();
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("testUser", "password")
                .postForEntity(
                        "http://localhost:" + port + "/helpdesk-service/comments",
                        commentDTO,
                        String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
