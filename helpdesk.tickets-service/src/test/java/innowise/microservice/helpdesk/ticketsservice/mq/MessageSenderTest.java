package innowise.microservice.helpdesk.ticketsservice.mq;

import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.ROUTING_KEY;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Test
    void sendMessage_shouldSendToRabbitMQWithCorrectArguments() {
        HistoryDTO historyDTO = new HistoryDTO();

        messageSender.sendMessage(historyDTO);

        verify(rabbitTemplate).convertAndSend(EXCHANGE, ROUTING_KEY, historyDTO);
    }
}
