package innowise.microservice.helpdesk.historyservice.service;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.exception.HistoryNotFoundException;
import innowise.microservice.helpdesk.historyservice.mapper.HistoryMapper;
import innowise.microservice.helpdesk.historyservice.repository.HistoryRepository;
import innowise.microservice.helpdesk.historyservice.services.HistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private HistoryMapper historyMapper;

    @InjectMocks
    private HistoryService historyService;

    @Test
    void createHistory_shouldCreateHistory() {
        HistoryDTO historyDTO = new HistoryDTO();
        History history = new History();
        when(historyMapper.toHistory(historyDTO)).thenReturn(history);

        historyService.createHistory(historyDTO);

        verify(historyMapper).toHistory(historyDTO);
        verify(historyRepository).insert(history);
    }

    @Test
    void getHistory_shouldReturnHistory_WhenHistoryExists() {
        int historyId = 1;
        History expectedHistory = new History();
        when(historyRepository.findById(historyId)).thenReturn(Optional.of(expectedHistory));

        Optional<History> actualHistory = historyService.getHistory(historyId);

        Assertions.assertTrue(actualHistory.isPresent());
        Assertions.assertEquals(expectedHistory, actualHistory.get());
    }

    @Test
    void getHistory_shouldThrowHistoryNotFoundException_WhenHistoryDoNotExist() {
        int historyId = 1;
        when(historyRepository.findById(historyId)).thenReturn(Optional.empty());

        assertThrows(HistoryNotFoundException.class, () -> historyService.getHistory(historyId));
    }
}
