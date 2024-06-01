package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.OrdersJPA;
import com.blps.lab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrdersJPA> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{orderID}/payment")
    public ResponseEntity<?> payTicket(@PathVariable Integer orderID) {
        orderService.payOrder(orderID);
        return ResponseEntity.ok("Ticket is paid!");
    }
}
