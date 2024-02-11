package innowise.microservice.helpdesk.ticketsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.services.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {
    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
    }

    @Test
    void createFeedback_shouldCreateFeedback() throws Exception {
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        User creator = TicketServiceTestHelper.createUser();

        doNothing().when(feedbackService).createFeedback(any(FeedbackDTO.class), any(User.class));

        mockMvc.perform(post("/helpdesk-service/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDTO))
                        .with(user(creator)))
                .andExpect(status().isOk());
    }

    @Test
    void getFeedback_shouldReturnOk() throws Exception {
        Long feedbackId = 1L;
        Feedback mockFeedback = new Feedback();

        when(feedbackService.getFeedbackById(feedbackId)).thenReturn(Optional.of(mockFeedback));

        mockMvc.perform(get("/helpdesk-service/feedback/{id}", feedbackId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getFeedback_shouldReturnNotFoundStatus_whenFeedbackDoesNotExist() throws Exception {
        int feedbackId = 2;

        mockMvc.perform(get("/{id}", feedbackId))
                .andExpect(status().isNotFound());
    }

}
