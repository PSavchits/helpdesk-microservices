package innowise.microservice.helpdesk.historyservice.controller;

import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.services.HistoryService;
import org.junit.jupiter.api.Assertions;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private HistoryController historyController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
    }


    @Test
    void getHistoriesByTicketId_shouldReturnHistories_whenHistoriesExists() throws Exception {
        int ticketId = 1;

        List<History> mockHistories = Collections.singletonList(new History());
        when(historyService.getHistoriesByTicketId(ticketId)).thenReturn(mockHistories);

        mockMvc.perform(get("/history-service/histories/{id}", ticketId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(historyService, times(1)).getHistoriesByTicketId(ticketId);
    }

    @Test
    void getHistoriesByTicketId_shouldReturnNotFound_whenHistoriesDoNotExist(){
        int ticketId = 2;

        ResponseEntity<List<History>> response = historyController.getHistoriesByTicketId(ticketId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()));
        Assertions.assertEquals(0, response.getBody().size());
    }
}
