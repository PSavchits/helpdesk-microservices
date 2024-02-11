package innowise.microservice.helpdesk.emailservice.mq;

import innowise.microservice.helpdesk.emailservice.dto.EmailDTO;
import innowise.microservice.helpdesk.emailservice.dto.FeedbackEmailDTO;
import innowise.microservice.helpdesk.emailservice.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static innowise.microservice.helpdesk.emailservice.utils.Constants.EMAIL_QUEUE;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_QUEUE;

@Component
@AllArgsConstructor
public class Receiver {

    private final EmailService emailService;

    @RabbitListener(queues = EMAIL_QUEUE)
    public void listenerEmail(EmailDTO emailDTO) {
        emailService.sendEmail(emailDTO.getTo(), emailDTO.getTicketId(), emailDTO.getState());
    }

    @RabbitListener(queues = FEEDBACK_QUEUE)
    public void listenerFeedback(FeedbackEmailDTO dto) {
        emailService.sendFeedbackEmail(dto.getTo(), dto.getTicketId());
    }
}