package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.TicketsJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.MovieService;
import com.blps.lab3.service.OrderService;
import com.blps.lab3.utils.JwtUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class TicketController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AccountsService accountsService;

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/{movieID}/ticket")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTicket(@PathVariable Integer movieID, @RequestBody TicketsJPA ticket) {
        Optional<TicketsJPA> ticketFound = movieService.getTicketByMovieID(movieID);
        if (ticketFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket of this movie already exists");
        }
        return ResponseEntity.ok(movieService.addTicketToMovie(movieID, ticket));
    }

    @GetMapping("/ticket")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<TicketsJPA> getTickets() {
        return movieService.getTickets();
    }

    @PostMapping("/{movieID}/ticket/buy")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> buyTicket(@PathVariable Integer movieID, HttpServletRequest request) {
        //Optional<AccountsJPA> account = accountsService.findAccountByID(order.getUser_id());
        Optional<TicketsJPA> ticket = movieService.getTicketByMovieID(movieID);

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String jwtToken = authorizationHeader.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);

        System.out.println(email);

        Optional<AccountsJPA> accountFound = accountsService.findAccountByEmail(email);

        if (accountFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exists!");
        } else if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket does not exists!");
        } else if (ticket.get().getAmount() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket is not available");
        }
        else {
            // Start Camunda Process
            java.util.Map<String, Object> variables = new java.util.HashMap<>();
            variables.put("movieId", movieID);
            variables.put("quantity", 1);
            variables.put("accountEmail", email);

            runtimeService.startProcessInstanceByKey("TicketPurchaseProcess", variables);

            return ResponseEntity.ok("Ticket purchase process started. Please complete booking in Tasklist.");
        }
    }
}
