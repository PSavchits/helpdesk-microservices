package innowise.microservice.helpdesk.ticketsservice.exception;

import innowise.microservice.helpdesk.ticketsservice.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FeedbackNotFoundException.class, TicketNotFoundException.class, CategoryNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return ErrorResponse.builder()
                .message(entityNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now()).build();
    }
}
