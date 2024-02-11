package innowise.microservice.helpdesk.ticketsservice.helpers;

import innowise.microservice.helpdesk.ticketsservice.dto.ChangeTicketStateDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import innowise.microservice.helpdesk.ticketsservice.enums.Urgency;

import java.time.LocalDate;

public class TicketServiceTestHelper {
    public static final int TICKET_ID = 1;
    public static final String TICKET_NAME = "Ticket 1";
    public static final String TICKET_DESCRIPTION = "Some Description";

    public static final State NEW_STATE = State.NEW;

    public static final Urgency HIGH_URGENCY = Urgency.HIGH;

    public static final LocalDate CREATED_DATE = LocalDate.of(2025, 4, 1);

    public static final String CATEGORY = "People Management";

    public static final User user = createUser();
    public static final Category TEST_CATEGORY = createCategory();

    public static TicketDTO createTicketDTO() {
        return TicketDTO.builder()
                .id(TICKET_ID)
                .name(TICKET_NAME)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(CREATED_DATE)
                .assignee(createUser())
                .owner(createUser())
                .state(NEW_STATE)
                .category(CATEGORY)
                .urgency(HIGH_URGENCY)
                .approver(createUser())
                .commentText("Test comment text")
                .build();
    }

    public static Category createCategory() {
        return Category.builder()
                .name("People Management")
                .build();
    }

    public static Ticket createTicket() {
        return Ticket.builder()
                .id(TICKET_ID)
                .name(TICKET_NAME)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(CREATED_DATE)
                .assignee(new User())
                .owner(new User())
                .state(NEW_STATE)
                .category(TEST_CATEGORY)
                .urgency(HIGH_URGENCY)
                .approver(new User())
                .build();
    }

    public static Ticket createTicketWithUser() {
        return Ticket.builder()
                .id(TICKET_ID)
                .name(TICKET_NAME)
                .description(TICKET_DESCRIPTION)
                .desiredResolutionDate(CREATED_DATE)
                .assignee(user)
                .owner(user)
                .state(NEW_STATE)
                .category(TEST_CATEGORY)
                .urgency(HIGH_URGENCY)
                .approver(user)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .firstname("name")
                .lastname("name")
                .email("test@yopmail.com")
                .role(Role.EMPLOYEE)
                .password("P@ssword1")
                .build();
    }

    public static ChangeTicketStateDTO createChangeTicketStateDTO() {
        return ChangeTicketStateDTO.builder()
                .newState(State.IN_PROGRESS.toString())
                .id(1)
                .build();
    }
}
