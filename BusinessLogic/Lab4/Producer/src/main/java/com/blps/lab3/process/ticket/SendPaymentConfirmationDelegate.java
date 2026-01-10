package com.blps.lab3.process.ticket;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.OrderService;
import com.blps.lab3.message.MessageProducerService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("sendPaymentConfirmationDelegate")
public class SendPaymentConfirmationDelegate implements JavaDelegate {

    @Autowired
    private MessageProducerService messageProducerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountsService accountsService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer orderId = (Integer) execution.getVariable("orderId");
        Integer accountId = (Integer) execution.getVariable("accountId");

        Optional<OrdersJPA> orderOpt = orderService.getOrderByID(orderId);
        Optional<AccountsJPA> accountOpt = accountsService.findAccountByID(accountId);

        if (orderOpt.isPresent() && accountOpt.isPresent()) {
            messageProducerService.sendPaymentMessage(orderOpt.get(), accountOpt.get());
        }
    }
}
