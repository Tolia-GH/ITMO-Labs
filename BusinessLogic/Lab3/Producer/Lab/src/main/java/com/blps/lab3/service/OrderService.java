package com.blps.lab3.service;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.databaseJPA.Objects.TicketsJPA;
import com.blps.lab3.databaseJPA.OrderStatus;
import com.blps.lab3.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab3.databaseJPA.Repositories.OrdersRepo;
import com.blps.lab3.databaseJPA.Repositories.TicketsRepo;
import com.blps.lab3.message.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private TicketsRepo ticketsRepo;

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private AccountsRepo accountsRepo;


    @Scheduled(cron = "0 */5 * * * *")
    public void autoCleanExpiredOrders() {
        System.out.println("Begin auto clean expired orders");

        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(5);

        List<OrdersJPA> unpaidOrders = ordersRepo.findAllUnpaidByExpireTime(expireTime);

        for (OrdersJPA order : unpaidOrders) {
            order.setStatus(OrderStatus.CANCELLED);
            ordersRepo.save(order);
            System.out.println("Order with id: " + order.getId() + " has been cancelled");
        }

        System.out.println("Finished auto clean expired orders");
    }

    @Transactional
    public void payOrder(Integer orderID, Integer accountID) {

        OrdersJPA order = ordersRepo.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PENDING)
            throw new RuntimeException("Order already paid or cancelled");

        TicketsJPA ticket = ticketsRepo.findById(order.getTicket_id())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getAmount() <= 0)
            throw new RuntimeException("No tickets left");

        // 更新库存与订单状态（同一事务中）
        ticket.setAmount(ticket.getAmount() - 1);
        ticketsRepo.save(ticket);

        order.setStatus(OrderStatus.COMPLETED);
        ordersRepo.save(order);

        // 注册事务提交后的回调
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                AccountsJPA account = accountsRepo.findById(accountID)
                        .orElseThrow(() -> new RuntimeException("Account not found"));

                // 在事务提交后异步发送MQTT消息
                messageProducerService.sendPaymentMessage(order, account);
            }
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
