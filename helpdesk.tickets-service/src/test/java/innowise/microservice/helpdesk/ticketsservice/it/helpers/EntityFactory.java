package innowise.microservice.helpdesk.ticketsservice.it.helpers;

import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;

import java.time.LocalDate;

public class EntityFactory {

    public static final User user = new User(2, "user2", "user2", "user2_mogilev@yopmail.com", "$2a$12$UBXPkMHbwkC5ZFUWrInSKu65ba.joR3ZsVv2BoMhHH.GXtFsHR4p2", Role.EMPLOYEE);
    public static final Ticket ticket = TicketServiceTestHelper.createTicket();
    public static final Feedback EXPECTED_FEEDBACK = new Feedback(1L, user, 5, LocalDate.of(2025, 4, 6), "Great service", ticket);

}
