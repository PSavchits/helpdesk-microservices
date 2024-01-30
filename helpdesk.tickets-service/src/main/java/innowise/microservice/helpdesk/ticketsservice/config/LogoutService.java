package innowise.microservice.helpdesk.ticketsservice.config;

import innowise.microservice.helpdesk.ticketsservice.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static innowise.microservice.helpdesk.ticketsservice.util.Constants.AUTHORIZATION_HEADER;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.BEARER_HEADER;
import static innowise.microservice.helpdesk.ticketsservice.util.Constants.TOKEN_STARTING_POSITION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(BEARER_HEADER)) {
            return;
        }
        jwt = authHeader.substring(TOKEN_STARTING_POSITION);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
