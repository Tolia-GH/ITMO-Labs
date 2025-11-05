package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.service.OrderService;
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
        orderService.payOrder(orderID, accountID);
        return ResponseEntity.ok("Ticket is paid!");
    }
}
