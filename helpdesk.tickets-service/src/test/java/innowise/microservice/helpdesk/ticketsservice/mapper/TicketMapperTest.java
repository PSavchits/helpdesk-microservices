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
        Assertions.assertNull(ticket.getAttachments());
        Assertions.assertNull(ticket.getComments());
    }

    @Test
    void updateTicketFromDto_shouldUpdateTicket() {
        TicketDTO ticketDTO = TicketServiceTestHelper.createTicketDTO();

        Ticket existingTicket = new Ticket();
        Category category = new Category();

        ticketMapper.updateTicketFromDto(ticketDTO, existingTicket, category);

        Assertions.assertNotNull(existingTicket);
        Assertions.assertEquals(TICKET_NAME, existingTicket.getName());
        Assertions.assertEquals(TICKET_DESCRIPTION, existingTicket.getDescription());
        Assertions.assertNull(existingTicket.getAssignee());
        Assertions.assertNull(existingTicket.getOwner());
        Assertions.assertNull(existingTicket.getApprover());
        Assertions.assertEquals(category, existingTicket.getCategory());
        Assertions.assertEquals(Urgency.HIGH, existingTicket.getUrgency());
    }
}
