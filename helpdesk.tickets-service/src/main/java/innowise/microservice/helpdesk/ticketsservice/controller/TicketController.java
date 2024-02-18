package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.dto.ChangeTicketStateDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketOverviewDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketReadDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.exception.TicketNotFoundException;
import innowise.microservice.helpdesk.ticketsservice.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/helpdesk-service/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<String> createTicket(@RequestPart(value = "ticketDTO") TicketDTO ticketDTO,
                                               @RequestParam(value = "attachments", required = false) List<MultipartFile> attachments,
                                               @AuthenticationPrincipal User creator) {
        ticketService.createTicket(ticketDTO, creator, attachments);
        return ResponseEntity.ok("Ticket created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editTicket(@PathVariable("id") int id,
                                             @RequestPart TicketDTO ticketDTO,
                                             @RequestParam(value = "attachments", required = false) List<MultipartFile> attachments,
                                             @AuthenticationPrincipal User creator) {

        ticketService.updateTicket(id, ticketDTO, creator, attachments);
        return ResponseEntity.ok("Ticket edited successfully");
    }

    @PostMapping("/changeTicketState")
    public ResponseEntity<String> editTicketState(@RequestBody ChangeTicketStateDTO request, @AuthenticationPrincipal User creator) {
        ticketService.editTicketState(request, creator);
        return ResponseEntity.ok("Ticket state edited successfully");
    }

    @GetMapping
    public ResponseEntity<List<TicketReadDTO>> findAllTickets(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<TicketReadDTO> tickets = ticketService.getTicketsByUser(page, size);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/myTickets")
    public ResponseEntity<List<TicketReadDTO>> findMyTickets(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        List<TicketReadDTO> tickets = ticketService.getMyTickets(page, size);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/overview/{id}")
    public ResponseEntity<TicketOverviewDTO> getTicketOverview(@PathVariable("id") int id) {
        TicketOverviewDTO ticketOverview = ticketService.getTicketOverviewById(id);
        return ResponseEntity.ok(ticketOverview);
    }

    @GetMapping("/showTicket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") int id) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(TicketNotFoundException::new);
        return ResponseEntity.ok().body(ticket);
    }
}
