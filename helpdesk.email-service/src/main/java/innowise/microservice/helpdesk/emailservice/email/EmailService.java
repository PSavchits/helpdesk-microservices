package innowise.microservice.helpdesk.emailservice.email;

public interface EmailService {
    void sendEmail(String to, Integer ticketId, String state);

    void sendFeedbackEmail(String to, Integer ticketId);
}
