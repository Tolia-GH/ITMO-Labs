package com.blps.lab3.process.ticket;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("processPaymentDelegate")
public class ProcessPaymentDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String paymentMethod = (String) execution.getVariable("paymentMethod");
        String cardNumber = (String) execution.getVariable("cardNumber");
        String cvv = (String) execution.getVariable("cvv");
        
        boolean success = true; 
        
        if ("CARD".equals(paymentMethod)) {
            if (cardNumber == null || cardNumber.isEmpty() || cvv == null || cvv.isEmpty()) {
                success = false; // Missing details
            } else if (cardNumber.endsWith("0000") || "000".equals(cvv)) {
                success = false; // Simulation of failure
            }
        }
        
        execution.setVariable("paymentSuccessful", success);
    }
}
