package innowise.microservice.helpdesk.ticketsservice.exception;

public class FeedbackNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "Feedback doesn't exist! feedbackId:%s";

    public FeedbackNotFoundException(int feedbackId) {
        super(String.format(DEFAULT_MESSAGE, feedbackId));
    }
}
