package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Urgency;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper.TICKET_DESCRIPTION;
import static innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper.TICKET_NAME;

@SpringBootTest
class TicketMapperTest {
    @Autowired
    private TicketMapper ticketMapper;

    @Test
    void ticketDtoToTicket_shouldReturnTicket() {
        TicketDTO ticketDTO = TicketServiceTestHelper.createTicketDTO();
        User creator = new User();
        Category category = new Category();

        Ticket ticket = ticketMapper.ticketDtoToTicket(ticketDTO, creator, category);

        Assertions.assertNotNull(ticket);
        Assertions.assertNull(ticket.getId());
        Assertions.assertEquals(TICKET_NAME, ticket.getName());
        Assertions.assertEquals(TICKET_DESCRIPTION, ticket.getDescription());
        Assertions.assertNotNull(ticket.getCreatedOn());
        Assertions.assertNull(ticket.getAssignee());
        Assertions.assertEquals(creator, ticket.getOwner());
        Assertions.assertNull(ticket.getApprover());
        Assertions.assertEquals(category, ticket.getCategory());
        Assertions.assertNotNull(ticket.getComments());
    }

    @Test
    void updateTicketFromDto_shouldUpdateTicket() {
        Ticket ticketTest = new Ticket();
        TicketDTO ticketDTO = TicketServiceTestHelper.createTicketDTO();
        Category category = new Category();

        Ticket ticket = ticketMapper.updateTicketFromDto(ticketDTO, category, ticketTest);

        Assertions.assertNotNull(ticket);
        Assertions.assertEquals(TICKET_NAME, ticket.getName());
        Assertions.assertEquals(TICKET_DESCRIPTION, ticket.getDescription());
        Assertions.assertNull(ticket.getAssignee());
        Assertions.assertNull(ticket.getOwner());
        Assertions.assertNull(ticket.getApprover());
        Assertions.assertEquals(category, ticket.getCategory());
        Assertions.assertEquals(Urgency.HIGH, ticket.getUrgency());
    }
}
