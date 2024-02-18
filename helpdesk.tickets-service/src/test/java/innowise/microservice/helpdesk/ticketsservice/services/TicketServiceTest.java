package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.ChangeTicketStateDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import innowise.microservice.helpdesk.ticketsservice.mapper.HistoryMapper;
import innowise.microservice.helpdesk.ticketsservice.mapper.TicketMapper;
import innowise.microservice.helpdesk.ticketsservice.mq.MessageSender;
import innowise.microservice.helpdesk.ticketsservice.repository.CategoryRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private HistoryMapper historyMapper;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private CommentService commentService;
    @Mock
    private MessageSender messageSender;

    @Test
    void getTicketsByCreatorAndApprover_shouldReturnPageOfTickets() {
        User creator = new User();
        User approver = new User();
        int page = 0;
        int size = 10;

        Page<Ticket> mockedPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()));
        when(ticketRepository.findByOwnerAndApprover(creator, approver, PageRequest.of(page, size))).thenReturn(mockedPage);

        Page<Ticket> ticketsPage = ticketService.getTicketsByCreatorAndApprover(creator, approver, page, size);

        assertEquals(2, ticketsPage.getContent().size());
        verify(ticketRepository).findByOwnerAndApprover(creator, approver, PageRequest.of(page, size));
    }


    @Test
    void getTicketsByApproverAndState_shouldReturnPageOfTickets() {
        User approver = new User();
        State state = State.NEW;
        int page = 0;
        int size = 10;

        Page<Ticket> mockedPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()));
        when(ticketRepository.findByApproverAndState(approver, state, PageRequest.of(page, size))).thenReturn(mockedPage);

        Page<Ticket> ticketsPage = ticketService.getTicketsByApproverAndState(approver, state, page, size);

        assertEquals(2, ticketsPage.getContent().size());
        verify(ticketRepository).findByApproverAndState(approver, state, PageRequest.of(page, size));
    }

    @Test
    void getApprovedTicketsCreatedByEmployeesAndManagers_shouldReturnPageOfTickets() {
        int page = 0;
        int size = 10;

        Page<Ticket> mockedPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()));
        when(ticketRepository.findByOwnerRoleInAndState(Arrays.asList(Role.EMPLOYEE, Role.MANAGER), State.APPROVED, PageRequest.of(page, size))).thenReturn(mockedPage);

        Page<Ticket> ticketsPage = ticketService.getApprovedTicketsCreatedByEmployeesAndManagers(page, size);

        assertEquals(2, ticketsPage.getContent().size());
        verify(ticketRepository).findByOwnerRoleInAndState(Arrays.asList(Role.EMPLOYEE, Role.MANAGER), State.APPROVED, PageRequest.of(page, size));
    }

    @Test
    void getTicketsByAssigneeAndState_shouldReturnPageOfTickets() {
        User assignee = new User();
        State state = State.IN_PROGRESS;
        int page = 0;
        int size = 10;

        Page<Ticket> mockedPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()));
        when(ticketRepository.findByAssigneeAndState(assignee, state, PageRequest.of(page, size))).thenReturn(mockedPage);

        Page<Ticket> ticketsPage = ticketService.getTicketsByAssigneeAndState(assignee, state, page, size);

        assertEquals(2, ticketsPage.getContent().size());
        verify(ticketRepository).findByAssigneeAndState(assignee, state, PageRequest.of(page, size));
    }


    @Test
    void getTicketsByAssignee_shouldReturnListOfTickets() {
        User assignee = new User();
        when(ticketRepository.findByAssignee(assignee)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getTicketsByAssignee(assignee);

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByAssignee(assignee);
    }

    @Test
    void getTicketsById_shouldReturnTicket() {
        int ticketId = 1;
        when(ticketRepository.findTicketById(ticketId)).thenReturn(Optional.of(new Ticket()));

        Optional<Ticket> ticket = ticketService.getTicketById(ticketId);

        assertNotNull(ticket);
        verify(ticketRepository).findTicketById(ticketId);
    }

    @Test
    void getNewTicketsWithEmployeeOwners_shouldReturnPageOfTickets() {
        State state = State.NEW;
        Role role = Role.EMPLOYEE;
        int page = 0;
        int size = 10;

        Page<Ticket> mockedPage = new PageImpl<>(Arrays.asList(new Ticket(), new Ticket()));
        when(ticketRepository.findByStateAndOwnerRole(state, role, PageRequest.of(page, size))).thenReturn(mockedPage);

        Page<Ticket> ticketsPage = ticketService.getNewTicketsWithEmployeeOwners(state, role, page, size);

        assertEquals(2, ticketsPage.getContent().size());
        verify(ticketRepository).findByStateAndOwnerRole(state, role, PageRequest.of(page, size));
    }


    @Test
    void getTicketsByOwner_shouldReturnListOfTickets() {
        User owner = new User();
        when(ticketRepository.findByOwner(owner)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getTicketsByOwner(owner);

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        verify(ticketRepository).findByOwner(owner);
    }

    @Test
    void editTicketState_shouldEditTicketState() {
        int ticketId = 1;
        State newState = State.IN_PROGRESS;
        ChangeTicketStateDTO changeTicketStateDTO = TicketServiceTestHelper.createChangeTicketStateDTO();
        User editor = new User();
        Ticket ticket = new Ticket();
        ticket.setOwner(new User());
        when(ticketRepository.findTicketById(ticketId)).thenReturn(Optional.of(ticket));

        ticketService.editTicketState(changeTicketStateDTO, editor);

        assertEquals(newState, ticket.getState());
        verify(ticketRepository).save(ticket);
    }
}
