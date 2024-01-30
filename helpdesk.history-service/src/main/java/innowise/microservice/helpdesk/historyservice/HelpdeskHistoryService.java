package innowise.microservice.helpdesk.historyservice;

import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.repository.HistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelpdeskHistoryService {

    public static void main(String[] args) {
        SpringApplication.run(HelpdeskHistoryService.class, args);
    }

}
