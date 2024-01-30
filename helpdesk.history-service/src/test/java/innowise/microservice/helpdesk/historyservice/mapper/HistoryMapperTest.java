package innowise.microservice.helpdesk.historyservice.mapper;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.helpers.ReceiverHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HistoryMapperTest {
    @Autowired
    private HistoryMapper historyMapper;

    @Test
    void ticketToHistory_shouldReturnHistory() {
        HistoryDTO historyDTO = ReceiverHelper.createHistoryDTO();

        History history = historyMapper.toHistory(historyDTO);

        Assertions.assertNotNull(history);
        Assertions.assertNull(history.getId());
        Assertions.assertNotNull(history.getCreated());
        Assertions.assertEquals(historyDTO.getAction(), history.getAction());
        Assertions.assertEquals(historyDTO.getTicketId(), history.getTicketId());
        Assertions.assertEquals(historyDTO.getUserId(), history.getUserId());
        Assertions.assertEquals(historyDTO.getDescription(), history.getDescription());
    }
}
