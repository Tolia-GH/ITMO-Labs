package com.blps.lab1.service;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import com.blps.lab1.databaseJPA.Repositories.OrdersRepo;
import com.blps.lab1.databaseJPA.Repositories.TicketsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
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

    public void payOrder(Integer orderID) {

        try {
            // 开启事务
            transactionManager.begin();

            // 执行订单支付逻辑
            ordersRepo.findById(orderID).ifPresent(order -> {
                order.setIs_paid(true);
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
//        ordersRepo.findById(orderID).map(order -> {
//            order.setIs_paid(true);
//            ticketsRepo.findById(order.getTicket_id()).map(ticket -> {
//                ticket.setAmount(ticket.getAmount() - 1);
//                return ticketsRepo.save(ticket);
//            });
//            return ordersRepo.save(order);
//        });
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
