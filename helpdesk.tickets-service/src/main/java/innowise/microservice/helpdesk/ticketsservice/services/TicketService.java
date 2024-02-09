package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.dto.ChangeTicketStateDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketOverviewDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketReadDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Attachment;
import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import innowise.microservice.helpdesk.ticketsservice.exception.CategoryNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.exception.TicketNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.mapper.HistoryMapper;
import innowise.microservice.helpdesk.ticketsservice.mapper.TicketMapper;
import innowise.microservice.helpdesk.ticketsservice.mq.MessageSender;
import innowise.microservice.helpdesk.ticketsservice.repository.CategoryRepository;
import innowise.microservice.helpdesk.ticketsservice.repository.TicketRepository;
import innowise.microservice.helpdesk.ticketsservice.services.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.CHANGE_STATE_ACTION;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.CREATE_TICKET_ACTION;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.UPDATE_TICKET_ACTION;

@Service
@AllArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final TicketMapper ticketMapper;
    private final EmailService emailService;
    private final AttachmentService attachmentService;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MessageSender messageSender;
    private final HistoryMapper historyMapper;

    public List<Ticket> getTicketsByOwner(User owner) {
        return ticketRepository.findByOwner(owner);
    }

    public List<Ticket> getTicketsByCreatorAndApprover(User owner, User approver) {
        return ticketRepository.findByOwnerAndApprover(owner, approver);
    }

    public List<Ticket> getTicketsByApproverAndState(User approver, State state) {
        return ticketRepository.findByApproverAndState(approver, state);
    }

    public List<Ticket> getApprovedTicketsCreatedByEmployeesAndManagers() {
        return ticketRepository.findByOwnerRoleInAndState(Arrays.asList(Role.EMPLOYEE, Role.MANAGER), State.APPROVED);
    }

    public List<Ticket> getTicketsByAssigneeAndState(User assignee, State state) {
        return ticketRepository.findByAssigneeAndState(assignee, state);
    }

    public List<Ticket> getTicketsByAssignee(User assignee) {
        return ticketRepository.findByAssignee(assignee);
    }

    public Optional<Ticket> getTicketById(int id) {
        return ticketRepository.findTicketById(id);
    }

    public List<Ticket> getNewTicketsWithEmployeeOwners(State state, Role role) {
        return ticketRepository.findByStateAndOwnerRole(state, role);
    }

    @Transactional
    public void createTicket(TicketDTO ticketDTO, User creator, List<MultipartFile> files) {
        Category category = categoryRepository.findByName(ticketDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(ticketDTO.getCategory()));

        Ticket ticket = ticketMapper.ticketDtoToTicket(ticketDTO, creator, category);

        ticket.getAttachments().addAll(attachmentService.attachFiles(files, ticket, creator));
        ticket.getComments().addAll(commentService.attachComments(ticketDTO, creator, ticket));

        ticketRepository.save(ticket);
        sendCreateUpdateMessage(ticket, creator, CREATE_TICKET_ACTION);
    }

    @Transactional
    public void updateTicket(int ticketId, TicketDTO editedTicketDTO, User editor, List<MultipartFile> files) {
        Ticket existingTicket = ticketRepository.findTicketById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        Category category = categoryRepository.findByName(editedTicketDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(editedTicketDTO.getCategory()));

        ticketMapper.updateTicketFromDto(editedTicketDTO, existingTicket, category);

        existingTicket.getAttachments().addAll(attachmentService.attachFiles(files, existingTicket, editor));
        existingTicket.getComments().addAll(commentService.attachComments(editedTicketDTO, editor, existingTicket));

        ticketRepository.save(existingTicket);
        sendCreateUpdateMessage(existingTicket, editor, UPDATE_TICKET_ACTION);
    }

    public void editTicketState(ChangeTicketStateDTO changeTicketStateDTO, User editor) {
        int id = changeTicketStateDTO.getId();

        State newState = State.valueOf(changeTicketStateDTO.getNewState());
        Ticket ticket = ticketRepository.findTicketById(id)
                .orElseThrow(TicketNotFoundException::new);
        String oldState = String.valueOf(ticket.getState());
        ticket.setState(newState);

        if (newState == State.CANCELED || newState == State.IN_PROGRESS) {
            ticket.setAssignee(editor);
        }

        if (newState == State.APPROVED || newState == State.DECLINED || newState == State.CANCELED) {
            ticket.setApprover(editor);
        }

        ticketRepository.save(ticket);

        HistoryDTO historyDTO = historyMapper.toHistoryDTO(editor.getId(), ticket.getId(), CHANGE_STATE_ACTION,
                "Ticket Status is changed from " + oldState + " to " + newState);
        emailService.sendEmail(ticket.getOwner().getEmail(), id, newState);
        messageSender.sendMessage(historyDTO);
    }

    public List<TicketReadDTO> getMyTickets() {
        User user = userService.getUserFromContextHolder();
        List<Ticket> tickets = ticketRepository.getTicketsByOwner(user);
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketReadDTO.class))
                .toList();
    }

    public List<TicketReadDTO> getTicketsByUser() {
        User user = userService.getUserFromContextHolder();
        return switch (user.getRole()) {
            case EMPLOYEE -> getTicketsByOwnerId(user);
            case MANAGER -> getAllManagerTickets(user);
            case ENGINEER -> getAllEngineerTickets(user);
        };
    }

    public List<TicketReadDTO> getTicketsByOwnerId(User user) {
        List<Ticket> tickets = ticketRepository.findByOwner(user);
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketReadDTO.class))
                .toList();
    }

    public List<TicketReadDTO> getAllManagerTickets(User user) {
        List<State> states = Arrays.asList(State.APPROVED, State.DECLINED, State.CANCELED, State.IN_PROGRESS, State.DONE);
        List<Ticket> ticketsByState = states.stream()
                .flatMap(state -> getTicketsByApproverAndState(user, state).stream())
                .toList();

        List<Ticket> managerTickets = getTicketsByCreatorAndApprover(user, user);
        List<Ticket> newTickets = getNewTicketsWithEmployeeOwners(State.NEW, Role.EMPLOYEE);

        Set<Ticket> uniqueTickets = new HashSet<>();
        uniqueTickets.addAll(managerTickets);
        uniqueTickets.addAll(newTickets);
        uniqueTickets.addAll(ticketsByState);

        return uniqueTickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketReadDTO.class))
                .toList();
    }

    public List<TicketReadDTO> getAllEngineerTickets(User user) {
        Set<Ticket> uniqueTickets = new HashSet<>();

        List<Ticket> approvedTickets = getApprovedTicketsCreatedByEmployeesAndManagers();
        List<Ticket> assignedTicketsInProgress = getTicketsByAssigneeAndState(user, State.IN_PROGRESS);
        List<Ticket> assignedTicketsDone = getTicketsByAssigneeAndState(user, State.DONE);

        uniqueTickets.addAll(approvedTickets);
        uniqueTickets.addAll(assignedTicketsInProgress);
        uniqueTickets.addAll(assignedTicketsDone);

        return uniqueTickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketReadDTO.class))
                .toList();
    }

    public TicketOverviewDTO getTicketOverviewById(int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Ticket ticket = getTicketById(id)
                .orElseThrow(TicketNotFoundException::new);

        Set<Attachment> attachments = attachmentService.getAttachmentsByTicketId(ticket);
        Set<Comment> comments = commentService.getCommentsByTicket(ticket.getId());

        return TicketOverviewDTO.builder()
                .feedback(ticket.getFeedback())
                .currentUser(currentUser)
                .ticket(ticket)
                .category(ticket.getCategory())
                .attachments(attachments)
                .comments(comments)
                .build();
    }

    public void sendCreateUpdateMessage(Ticket ticket, User creator, String action) {
        HistoryDTO historyDTO = historyMapper.toHistoryDTO(ticket.getId(), creator.getId(), action);
        messageSender.sendMessage(historyDTO);
    }
}
