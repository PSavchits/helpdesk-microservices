package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.enums.Role;
import innowise.microservice.helpdesk.ticketsservice.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByOwner(User owner);

    Page<Ticket> findByOwner(User owner, Pageable pageable);

    Page<Ticket> findByStateAndOwnerRole(State state, Role role, Pageable pageable);

    Page<Ticket> findByOwnerAndApprover(User owner, User approver, Pageable pageable);

    Page<Ticket> findByApproverAndState(User approver, State state, Pageable pageable);

    Page<Ticket> findByAssigneeAndState(User assignee, State state, Pageable pageable);

    List<Ticket> findByAssignee(User assignee);

    Page<Ticket> findByOwnerRoleInAndState(List<Role> ownerRoles, State state, Pageable pageable);

    Optional<Ticket> findTicketById(int id);

    Page<Ticket> getTicketsByOwner(User user, Pageable pageable);
}
