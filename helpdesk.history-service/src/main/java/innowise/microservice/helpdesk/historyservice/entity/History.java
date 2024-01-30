package innowise.microservice.helpdesk.historyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class History {

    @Id
    private String id;
    private Integer ticketId;
    private LocalDate created;
    private String action;
    private Integer userId;
    private String description;

}
