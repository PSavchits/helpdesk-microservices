package innowise.microservice.helpdesk.emailservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
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

    public static final String EMAIL_EXCHANGE = "email_exchange";
    public static final String FEEDBACK_EXCHANGE = "feedback_exchange";

    public static final String EMAIL_QUEUE = "email_queue";
    public static final String EMAIL_ROUTING_KEY = "message_routingKey";

    public static final String FEEDBACK_QUEUE = "feedback_queue";
    public static final String FEEDBACK_ROUTING_KEY = "message_routingKey";
}
