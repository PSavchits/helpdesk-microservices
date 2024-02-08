package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.dto.TicketReadDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByOwner(User owner);

    List<Ticket> findByStateAndOwnerRole(State state, Role role);

    List<Ticket> findByOwnerAndApprover(User owner, User approver);

    List<Ticket> findByApproverAndState(User approver, State state);

    List<Ticket> findByAssigneeAndState(User assignee, State state);

    List<Ticket> findByAssignee(User assignee);

    List<Ticket> findByOwnerRoleInAndState(List<Role> ownerRoles, State state);

    Optional<Ticket> findTicketById(int id);

    List<Ticket> getTicketsByOwner(User user);
}
