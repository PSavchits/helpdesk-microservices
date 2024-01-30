package innowise.microservice.helpdesk.ticketsservice.services;

import innowise.microservice.helpdesk.ticketsservice.entity.Category;
import innowise.microservice.helpdesk.ticketsservice.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
