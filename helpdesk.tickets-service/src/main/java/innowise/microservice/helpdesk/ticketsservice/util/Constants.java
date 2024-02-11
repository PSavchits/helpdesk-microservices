package innowise.microservice.helpdesk.ticketsservice.util;

import innowise.microservice.helpdesk.ticketsservice.enums.State;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Constants {
    public static final int TOKEN_EXPIRATION = 144000;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_HEADER = "Bearer ";
    public static final int TOKEN_STARTING_POSITION = 7;
    public static final List<State> MANAGERS_STATES = List.of(State.APPROVED, State.DECLINED, State.CANCELED, State.IN_PROGRESS, State.DONE);
    public static final String NEW_STATE_SUBJECT = "New ticket for approval";
    public static final String NEW_STATE_TEXT = "Dear Managers,%n%nNew ticket %d is waiting for your approval.";
    public static final String APPROVED_STATE_SUBJECT = "Ticket was approved";
    public static final String APPROVED_STATE_TEXT = "Dear Users,%n%nTicket %d was approved by the Manager.";
    public static final String DECLINED_STATE_SUBJECT = "Ticket was declined";
    public static final String DECLINED_STATE_TEXT = "Dear User,%n%nTicket %d was declined by the Manager.";
    public static final String CANCELED_STATE_SUBJECT = "Ticket was cancelled";
    public static final String CANCELED_STATE_TEXT = "Dear User,%n%nTicket %d was cancelled.";
    public static final String DONE_STATE_SUBJECT = "Ticket was done";
    public static final String DONE_STATE_TEXT = "Dear User,%n%nYour ticket %d was done.";
    public static final String FEEDBACK_SUBJECT = "Feedback was provided";
    public static final String FEEDBACK_TEXT = "Dear User,%n%nThe feedback was provided on ticket %d.";
    public static final String CHANGE_STATE_ACTION = "Ticket Status is changed";
    public static final String CREATE_TICKET_ACTION = "Ticket created";
    public static final String UPDATE_TICKET_ACTION = "Ticket updated";
    public static final String ATTACH_FILE_ACTION = "File is attached";

    public static final String QUEUE = "message_queue";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "message_routingKey";

    public static final String EMAIL_QUEUE = "email_queue";
    public static final String EMAIL_EXCHANGE = "email_exchange";
    public static final String EMAIL_ROUTING_KEY = "message_routingKey";

    public static final String FEEDBACK_QUEUE = "feedback_queue";
    public static final String FEEDBACK_EXCHANGE = "feedback_exchange";
    public static final String FEEDBACK_ROUTING_KEY = "message_routingKey";
}
