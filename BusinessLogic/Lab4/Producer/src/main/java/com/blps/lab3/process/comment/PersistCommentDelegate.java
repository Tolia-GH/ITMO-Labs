package com.blps.lab3.process.comment;

import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.CommentService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("persistCommentDelegate")
public class PersistCommentDelegate implements JavaDelegate {

    @Autowired
    private CommentService commentService;
    @Autowired
    private AccountsService accountsService;
    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String content = (String) execution.getVariable("content");
        
        Integer movieId;
        Object mid = execution.getVariable("movieId");
        if (mid instanceof String) {
            movieId = Integer.parseInt((String) mid);
        } else {
            movieId = (Integer) mid;
        }

        // Get current user (initiator) or from variable if set
        // For simplicity, we assume the user email was passed as "userEmail" when starting process
        // OR we can try to get the assignee of the previous user task if available
        // BUT, better to pass "userEmail" when starting process, OR rely on identity service if authenticated
        
        String userEmail = (String) execution.getVariable("userEmail");
        if (userEmail == null) {
             // Fallback: try to get initiator
             userEmail = (String) execution.getVariable("initiator");
        }
        
        if (userEmail == null) {
            throw new RuntimeException("User email not found in process variables");
        }

        Integer userId = accountsService.findAccountByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getId();

        CommentJPA comment = commentService.persistComment(content, movieId, userId);
        
        execution.setVariable("commentId", comment.getId());
    }
}
