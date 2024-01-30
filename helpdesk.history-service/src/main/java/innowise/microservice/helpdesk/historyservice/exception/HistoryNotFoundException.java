package innowise.microservice.helpdesk.historyservice.exception;

public class HistoryNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "History doesn't exist! ticketId:%s";

    public HistoryNotFoundException(int ticketId) {
        super(String.format(DEFAULT_MESSAGE, ticketId));
    }
}
