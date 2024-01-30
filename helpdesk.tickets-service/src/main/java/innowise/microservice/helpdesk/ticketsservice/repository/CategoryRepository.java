package innowise.microservice.helpdesk.ticketsservice.repository;

import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String category);

    Optional<Category> findById(int id);
}
