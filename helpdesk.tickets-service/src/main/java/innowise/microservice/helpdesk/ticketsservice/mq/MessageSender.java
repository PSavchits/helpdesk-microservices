package innowise.microservice.helpdesk.ticketsservice.mq;

import innowise.microservice.helpdesk.ticketsservice.dto.EmailDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackEmailDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EMAIL_EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EMAIL_ROUTING_KEY;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.FEEDBACK_EXCHANGE;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.FEEDBACK_ROUTING_KEY;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.ROUTING_KEY;

@Service
@AllArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(HistoryDTO historyDTO) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, historyDTO);
    }

    public void sendEmail(String to, Integer ticketId, String state) {
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_ROUTING_KEY, new EmailDTO(to, ticketId, state));
    }

    public void sendFeedbackEmail(String to, Integer ticketId) {
        rabbitTemplate.convertAndSend(FEEDBACK_EXCHANGE, FEEDBACK_ROUTING_KEY, new FeedbackEmailDTO(to, ticketId));
    }
}
