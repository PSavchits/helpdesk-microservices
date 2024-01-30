package innowise.microservice.helpdesk.historyservice.controller;

import innowise.microservice.helpdesk.historyservice.entity.History;
import innowise.microservice.helpdesk.historyservice.services.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/history-service/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{id}")
    public ResponseEntity<List<History>> getHistoriesByTicketId(@PathVariable int id) {
        return ResponseEntity.ok(historyService.getHistoriesByTicketId(id));
    }
}
