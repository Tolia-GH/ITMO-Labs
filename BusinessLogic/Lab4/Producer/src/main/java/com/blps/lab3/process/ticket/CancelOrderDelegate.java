package com.blps.lab3.process.ticket;

import com.blps.lab3.databaseJPA.Objects.OrdersJPA;
import com.blps.lab3.databaseJPA.OrderStatus;
import com.blps.lab3.databaseJPA.Repositories.OrdersRepo;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("cancelOrderDelegate")
public class CancelOrderDelegate implements JavaDelegate {

    @Autowired
    private OrdersRepo ordersRepo;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer orderId = (Integer) execution.getVariable("orderId");
        
        // orderId might be null if cancellation happens before order creation (e.g. out of stock -> stop)
        // But the BPMN flow shows cancellation is only after creation or timeout.
        // Wait, "EndEvent_StoppedNoStock" is reached via "Flow_RetryBooking_No" from "Gateway_RetryBooking".
        // And "Task_CancelOrder" is reached via "Flow_Timeout_To_CancelOrder" and "Flow_RetryPayment_No".
        // Both imply order was created.
        // However, if "Load ticket" fails (stock not available) -> OutOfStock -> No retry -> EndEvent_StoppedNoStock.
        // In that path, no order is created. So CancelOrderDelegate is NOT called.
        // So orderId should be present.
        
        if (orderId != null) {
            Optional<OrdersJPA> orderOpt = ordersRepo.findById(orderId);
            if (orderOpt.isPresent()) {
                OrdersJPA order = orderOpt.get();
                if (order.getStatus() == OrderStatus.PENDING) {
                    order.setStatus(OrderStatus.CANCELLED);
                    ordersRepo.save(order);
                }
            }
        }
    }
}
