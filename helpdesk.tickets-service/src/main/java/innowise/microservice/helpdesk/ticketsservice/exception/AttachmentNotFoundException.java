package innowise.microservice.helpdesk.ticketsservice.exception;

public class AttachmentNotFoundException extends EntityNotFoundException{
    private static final String DEFAULT_MESSAGE = "Attachment doesn't exist!";

    public AttachmentNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
