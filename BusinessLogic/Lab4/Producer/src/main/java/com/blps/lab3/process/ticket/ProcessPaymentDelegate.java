package com.blps.lab3.process.ticket;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("processPaymentDelegate")
public class ProcessPaymentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String cardNumber = (String) execution.getVariable("cardNumber");
        
        boolean success = true; 
        if ("error".equals(cardNumber)) {
            success = false;
        }

        execution.setVariable("paymentSuccessful", success);
    }
}
