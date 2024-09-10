package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.databaseJPA.Repositories.OrdersRepo;
import com.blps.lab1.databaseJPA.Repositories.TicketsRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrdersRepo ordersRepo;
    private final TicketsRepo ticketsRepo;

    public OrderService(OrdersRepo ordersRepo,
                        TicketsRepo ticketsRepo) {
        this.ordersRepo = ordersRepo;
        this.ticketsRepo = ticketsRepo;
    }

    @Transactional
    public void payOrder(Integer orderID) {
        ordersRepo.findById(orderID).map(order -> {
            order.setIs_paid(true);
            ticketsRepo.findById(order.getTicket_id()).map(ticket -> {
                ticket.setAmount(ticket.getAmount() - 1);
                return ticketsRepo.save(ticket);
            });
            return ordersRepo.save(order);
        });
    }

    public List<OrdersJPA> getAllOrders() {
        return ordersRepo.findAll();
    }

    public Optional<OrdersJPA> getOrderByID(Integer id) {
        return ordersRepo.findById(id);
    }

    public List<OrdersJPA> getAllOrdersByAccountID(Integer accountID) {
        return ordersRepo.findAllByAccountId(accountID);
    }
}
