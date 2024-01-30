package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.MapperHelper;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void addComment_shouldCreateComment_whenValidCommentDTOAndAuthenticatedUser() {
        CommentDTO commentDTO = MapperHelper.createCommentDTO();
        User user = new User();

        ResponseEntity<String> response = commentController.addComment(commentDTO, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment created successfully", response.getBody());
    }

    @Test
    void addComment_shouldReturnNotFound_whenInvalidCommentDTO() throws Exception {
        CommentDTO commentDTO = MapperHelper.createCommentDTO();
        User creator = TicketServiceTestHelper.createUser();

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO))
                        .with(user(creator)))
                .andExpect(status().isNotFound());
    }
}
