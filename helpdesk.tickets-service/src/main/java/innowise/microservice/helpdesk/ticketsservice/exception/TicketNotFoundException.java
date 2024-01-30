package innowise.microservice.helpdesk.ticketsservice.exception;

public class TicketNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Ticket doesn't exist!";

    public TicketNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
