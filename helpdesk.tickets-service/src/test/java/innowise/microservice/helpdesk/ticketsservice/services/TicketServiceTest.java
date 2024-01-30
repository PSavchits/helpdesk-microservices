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
import innowise.microservice.helpdesk.ticketsservice.services.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private EmailService emailService;
    @Mock
    private AttachmentService attachmentService;
    @Mock
    private CommentService commentService;
    @Mock
    private MessageSender messageSender;

    @Test
    void getTicketsByCreatorAndApprover_shouldReturnListOfTickets() {
        User creator = new User();
        User approver = new User();
        when(ticketRepository.findByOwnerAndApprover(creator, approver)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getTicketsByCreatorAndApprover(creator, approver);

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByOwnerAndApprover(creator, approver);
    }

    @Test
    void getTicketsByApproverAndState_shouldReturnListOfTickets() {
        User approver = new User();
        State state = State.APPROVED;
        when(ticketRepository.findByApproverAndState(approver, state)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getTicketsByApproverAndState(approver, state);

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByApproverAndState(approver, state);
    }

    @Test
    void getApprovedTicketsCreatedByEmployeesAndManagers_shouldReturnListOfTickets() {
        when(ticketRepository.findByOwnerRoleInAndState(any(), any())).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getApprovedTicketsCreatedByEmployeesAndManagers();

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByOwnerRoleInAndState(Arrays.asList(Role.EMPLOYEE, Role.MANAGER), State.APPROVED);
    }

    @Test
    void getTicketsByAssigneeAndState_shouldReturnListOfTickets() {
        User assignee = new User();
        State state = State.IN_PROGRESS;
        when(ticketRepository.findByAssigneeAndState(assignee, state)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getTicketsByAssigneeAndState(assignee, state);

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByAssigneeAndState(assignee, state);
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
    void getNewTicketsWithEmployeeOwners_shouldReturnListOfTickets() {
        State state = State.NEW;
        when(ticketRepository.findByStateAndOwnerRole(state, Role.EMPLOYEE)).thenReturn(Arrays.asList(new Ticket(), new Ticket()));

        List<Ticket> tickets = ticketService.getNewTicketsWithEmployeeOwners(state, Role.EMPLOYEE);

        assertEquals(2, tickets.size());
        verify(ticketRepository).findByStateAndOwnerRole(state, Role.EMPLOYEE);
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
    void createTicket_shouldCreateTicket() {
        TicketDTO ticketDTO = TicketServiceTestHelper.createTicketDTO();
        User creator = new User();
        List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class), mock(MultipartFile.class));
        when(categoryRepository.findByName(any())).thenReturn(Optional.of(new Category()));
        when(ticketMapper.ticketDtoToTicket(any(), any(), any())).thenReturn(new Ticket());

        ticketService.createTicket(ticketDTO, creator, files);

        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void updateTicket_shouldUpdateTicket() {
        int ticketId = 1;
        TicketDTO editedTicketDTO = TicketServiceTestHelper.createTicketDTO();
        User editor = new User();
        List<MultipartFile> files = Arrays.asList(mock(MultipartFile.class), mock(MultipartFile.class));
        when(ticketRepository.findTicketById(ticketId)).thenReturn(Optional.of(new Ticket()));
        when(categoryRepository.findByName(any())).thenReturn(Optional.of(new Category()));

        ticketService.updateTicket(ticketId, editedTicketDTO, editor, files);

        verify(ticketRepository).save(any(Ticket.class));
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
