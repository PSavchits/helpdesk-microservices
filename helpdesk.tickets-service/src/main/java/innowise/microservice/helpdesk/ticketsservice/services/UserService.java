package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.exception.UserNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserFromContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();

        return userRepository.findUserByEmail(loggedInUserEmail)
                .orElseThrow(() -> new UserNotFoundException(loggedInUserEmail));
    }
}
