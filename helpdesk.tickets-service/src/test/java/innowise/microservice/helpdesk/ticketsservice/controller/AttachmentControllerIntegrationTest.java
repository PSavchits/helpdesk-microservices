package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.mapper.TicketMapper;
import innowise.microservice.helpdesk.ticketsservice.repository.AttachmentRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.AttachmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.Objects;

import static innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper.ATTACHMENT_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class AttachmentControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private TicketRepository ticketRepository;

    private Long attachmentId;

    @BeforeEach
    void setup() {
        Ticket testTicket = TicketServiceTestHelper.createTicket();
        ticketRepository.save(testTicket);

        byte[] testBlob = "Test Attachment Content".getBytes();
        Attachment attachment = new Attachment();
        attachment.setName(ATTACHMENT_NAME);
        attachment.setBlob(testBlob);
        attachment.setTicket(testTicket);

        attachmentRepository.save(attachment);

        attachmentId = attachment.getId();
    }

    @Test
    void downloadAttachment_shouldReturnAttachment_whenAttachmentExists() {

        ResponseEntity<Resource> response = restTemplate.exchange(
                "http://localhost:" + port + "/helpdesk-service/attachments/" + attachmentId,
                HttpMethod.GET,
                null,
                Resource.class);

        assertThat(response.getBody()).isNotNull();
        Assertions.assertEquals(ATTACHMENT_NAME, response.getBody().getFilename());
    }

    @Test
    void downloadAttachment_WhenAttachmentDoesNotExist() throws IOException {
        ResponseEntity<Resource> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/helpdesk-service/attachments/9999",
                Resource.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assertThat(Objects.requireNonNull(response.getBody()).contentLength()).isEqualTo(134L);
    }
}
