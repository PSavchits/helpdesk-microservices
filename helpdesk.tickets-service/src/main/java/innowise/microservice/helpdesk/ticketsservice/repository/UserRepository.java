package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String loggedInUserEmail);
}
