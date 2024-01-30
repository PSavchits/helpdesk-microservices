package innowise.microservice.helpdesk.historyservice.mq;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.services.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static innowise.microservice.helpdesk.historyservice.util.Constants.QUEUE;

@Component
@AllArgsConstructor
public class Receiver {

    private final HistoryService historyService;

    @RabbitListener(queues = QUEUE)
    public void listener(HistoryDTO historyDTO) {
        historyService.createHistory(historyDTO);
    }

}