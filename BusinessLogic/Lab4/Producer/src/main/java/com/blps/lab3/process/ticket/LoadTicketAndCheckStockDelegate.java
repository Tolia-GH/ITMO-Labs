package com.blps.lab3.process.ticket;

import com.blps.lab3.databaseJPA.Objects.TicketsJPA;
import com.blps.lab3.service.MovieService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("loadTicketAndCheckStockDelegate")
public class LoadTicketAndCheckStockDelegate implements JavaDelegate {

    @Autowired
    private MovieService movieService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer movieId = (Integer) execution.getVariable("movieId");
        Integer quantity = (Integer) execution.getVariable("quantity");
        
        if (movieId == null) {
             Object mid = execution.getVariable("movieId");
             if (mid instanceof String) movieId = Integer.parseInt((String) mid);
        }
        if (quantity == null) {
             Object qty = execution.getVariable("quantity");
             if (qty instanceof String) quantity = Integer.parseInt((String) qty);
             else quantity = 1;
        }

        Optional<TicketsJPA> ticketOpt = movieService.getTicketByMovieID(movieId);

        boolean stockAvailable = false;
        if (ticketOpt.isPresent()) {
            TicketsJPA ticket = ticketOpt.get();
            if (ticket.getAmount() >= quantity) {
                stockAvailable = true;
                execution.setVariable("ticketId", ticket.getId());
                execution.setVariable("ticketPrice", ticket.getPrice());
            }
        }

        execution.setVariable("stockAvailable", stockAvailable);
    }
}
