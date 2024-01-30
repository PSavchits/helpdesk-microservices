package innowise.microservice.helpdesk.historyservice.mq;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.helpers.ReceiverHelper;
import innowise.microservice.helpdesk.historyservice.services.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceiverTest {

    @InjectMocks
    private Receiver receiver;

    @Mock
    private HistoryService historyService;

    @Test
    void listener_shouldCallCreateHistory_WithCorrectArgument() {
        HistoryDTO historyDTO = ReceiverHelper.createHistoryDTO();

        receiver.listener(historyDTO);

        verify(historyService).createHistory(historyDTO);
    }

    @Test
    void listener_throwException_WhenCreateHistoryMethodThrowsException() {
        HistoryDTO historyDTO = new HistoryDTO();
        doThrow(new RuntimeException()).when(historyService).createHistory(historyDTO);

        assertThrows(RuntimeException.class, () -> receiver.listener(historyDTO));
    }
}
