package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.databaseJPA.OrderStatus;
import com.blps.lab1.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab1.databaseJPA.Repositories.OrdersRepo;
import com.blps.lab1.databaseJPA.Repositories.TicketsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionManager;
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
    private MailService mailService;
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


    public void payOrder(Integer orderID, Integer accountID) {

        try {
            // 开启事务
            transactionManager.begin();

            // 执行订单支付逻辑
            ordersRepo.findById(orderID).ifPresent(order -> {
                if (order.getStatus().equals(OrderStatus.PENDING)) {}
                order.setStatus(OrderStatus.COMPLETED);
                ticketsRepo.findById(order.getTicket_id()).ifPresent(ticket -> {
                    ticket.setAmount(ticket.getAmount() - 1);
                    ticketsRepo.save(ticket);
                });
                ordersRepo.save(order);
            });

            // 提交事务
            transactionManager.commit();
            System.out.println("Transaction committed successfully.");
        } catch (Exception e) {
            try {
                // 出现异常时回滚事务
                transactionManager.rollback();
                System.out.println("Transaction rolled back due to an error.");
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        }

        AccountsJPA account = accountsRepo.findById(accountID).orElse(null);
        OrdersJPA order = ordersRepo.findById(orderID).orElse(null);

        mailService.sendMail("Order Confirmation", order, account);
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
