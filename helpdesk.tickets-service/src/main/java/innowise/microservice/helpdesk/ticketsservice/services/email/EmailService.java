package innowise.microservice.helpdesk.ticketsservice.services.email;


import innowise.microservice.helpdesk.ticketsservice.enums.State;

public interface EmailService {
    void sendEmail(String to, Integer ticketId, State state);

    void sendFeedbackEmail(String to, Integer ticketId);
}
