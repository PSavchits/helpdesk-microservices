package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Modifying
    @Query("DELETE FROM Token t WHERE t.user.id = :userId")
    void deleteTokensByUserId(@Param("userId") Integer userId);

    Optional<Token> findByToken(String token);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Token t WHERE t.user.id = :userId")
    boolean existsTokenByUserId(@Param("userId") Integer userId);
}
