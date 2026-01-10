package com.blps.lab3.process.ticket;

import com.blps.lab3.service.OrderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("completeOrderDelegate")
public class CompleteOrderDelegate implements JavaDelegate {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer orderId = (Integer) execution.getVariable("orderId");
        
        orderService.completeOrder(orderId);
    }
}
