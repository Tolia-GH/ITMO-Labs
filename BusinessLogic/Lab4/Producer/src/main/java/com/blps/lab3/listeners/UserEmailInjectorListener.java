package com.blps.lab3.listeners;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEmailInjectorListener implements TaskListener {

    @Autowired
    private IdentityService identityService;

    // Field Injection
    private Expression variableName;

    @Override
    public void notify(DelegateTask delegateTask) {
        // Determine target variable name first
        String targetVar = "userEmail";
        if (variableName != null) {
            Object val = variableName.getValue(delegateTask);
            if (val != null) {
                targetVar = (String) val;
            }
        }

        String userId = null;

        // 1. Check if the variable is already set (e.g. by camunda:initiator)
        if (delegateTask.hasVariable(targetVar)) {
            Object varVal = delegateTask.getVariable(targetVar);
            if (varVal instanceof String) {
                userId = (String) varVal;
            }
        }

        // 2. Try to get authenticated user
        if (userId == null && identityService.getCurrentAuthentication() != null) {
            userId = identityService.getCurrentAuthentication().getUserId();
        }

        // 3. Fallback: try to get assignee if set manually
        if (userId == null) {
            userId = delegateTask.getAssignee();
        }

        if (userId != null) {
            // Ensure variable is set
            if (!delegateTask.hasVariable(targetVar)) {
                delegateTask.setVariable(targetVar, userId);
                System.out.println("Automatically injected " + targetVar + ": " + userId);
            }

            // Ensure task is assigned (if it's a user task and unassigned)
            if (delegateTask.getAssignee() == null) {
                delegateTask.setAssignee(userId);
                System.out.println("Automatically assigned task " + delegateTask.getName() + " to user: " + userId);
            }
        } else {
            System.err.println("Could not determine user email for task: " + delegateTask.getId());
        }
    }
}
