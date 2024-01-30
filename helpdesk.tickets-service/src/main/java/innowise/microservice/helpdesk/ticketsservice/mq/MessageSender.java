package innowise.microservice.helpdesk.ticketsservice.mq;

import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.ROUTING_KEY;

@Service
@AllArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(HistoryDTO historyDTO) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, historyDTO);
    }
}
