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
        // Try to get authenticated user (the one who clicked "Complete")
        String userId = null;
        if (identityService.getCurrentAuthentication() != null) {
            userId = identityService.getCurrentAuthentication().getUserId();
        }
        
        // Fallback: try to get assignee if authenticated user is null (e.g. API call)
        if (userId == null) {
            userId = delegateTask.getAssignee();
        }

        if (userId != null) {
            // Determine target variable name
            String targetVar = "userEmail";
            if (variableName != null) {
                targetVar = (String) variableName.getValue(delegateTask);
            }

            // Since our User ID is the Email, we can directly use it
            delegateTask.setVariable(targetVar, userId);
            System.out.println("Automatically injected " + targetVar + ": " + userId);
        } else {
            System.err.println("Could not determine user email for task: " + delegateTask.getId());
        }
    }
}