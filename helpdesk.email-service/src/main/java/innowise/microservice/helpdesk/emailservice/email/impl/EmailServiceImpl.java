package innowise.microservice.helpdesk.emailservice.email.impl;

import innowise.microservice.helpdesk.emailservice.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static innowise.microservice.helpdesk.emailservice.utils.Constants.APPROVED_STATE_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.APPROVED_STATE_TEXT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.CANCELED_STATE_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.CANCELED_STATE_TEXT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.DECLINED_STATE_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.DECLINED_STATE_TEXT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.DONE_STATE_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.DONE_STATE_TEXT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.FEEDBACK_TEXT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.NEW_STATE_SUBJECT;
import static innowise.microservice.helpdesk.emailservice.utils.Constants.NEW_STATE_TEXT;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String to, Integer ticketId, String state) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);

        switch (state) {
            case "NEW" -> compileMessage(message, NEW_STATE_SUBJECT, NEW_STATE_TEXT, ticketId);
            case "APPROVED" -> compileMessage(message, APPROVED_STATE_SUBJECT, APPROVED_STATE_TEXT, ticketId);
            case "DECLINED" -> compileMessage(message, DECLINED_STATE_SUBJECT, DECLINED_STATE_TEXT, ticketId);
            case "CANCELED" -> compileMessage(message, CANCELED_STATE_SUBJECT, CANCELED_STATE_TEXT, ticketId);
            case "DONE" -> compileMessage(message, DONE_STATE_SUBJECT, DONE_STATE_TEXT, ticketId);
        }
        emailSender.send(message);
    }

    private void compileMessage(SimpleMailMessage message, String state, String text, Integer ticketId) {
        message.setSubject(state);
        message.setText(String.format(text, ticketId));
    }

    @Override
    public void sendFeedbackEmail(String to, Integer ticketId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(FEEDBACK_SUBJECT);
        String text = String.format(FEEDBACK_TEXT, ticketId);
        message.setText(text);
        emailSender.send(message);
    }
}
