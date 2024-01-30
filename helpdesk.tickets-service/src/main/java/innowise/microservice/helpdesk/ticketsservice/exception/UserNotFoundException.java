package innowise.microservice.helpdesk.ticketsservice.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String DEFAULT_MESSAGE = "This user doesn't exist! email:%s";

    public UserNotFoundException(String email) {
        super(String.format(DEFAULT_MESSAGE, email));
    }
}
