package com.blps.lab3.process.ticket;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.MovieService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("createOrderDelegate")
public class CreateOrderDelegate implements JavaDelegate {

    @Autowired
    private MovieService movieService;
    @Autowired
    private AccountsService accountsService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer ticketId = (Integer) execution.getVariable("ticketId");
        String email = (String) execution.getVariable("accountEmail");

        Optional<AccountsJPA> accountOpt = accountsService.findAccountByEmail(email);
        
        if (accountOpt.isEmpty()) {
            throw new RuntimeException("Account not found for email: " + email);
        }

        OrdersJPA order = movieService.buyTicket(ticketId, accountOpt.get());
        
        execution.setVariable("orderId", order.getId());
        execution.setVariable("accountId", accountOpt.get().getId());
    }
}
