package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrdersJPA> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/account/{accountID}/order")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<OrdersJPA> getAllOrdersByAccountID(@PathVariable Integer accountID) {
        return orderService.getAllOrdersByAccountID(accountID);
    }

    @PutMapping("/account/{accountID}/order/{orderID}/payment")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> payTicket(@PathVariable Integer accountID, @PathVariable Integer orderID) {
        try {
            orderService.payOrder(orderID, accountID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Ticket is paid!");
    }
}
