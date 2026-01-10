package com.blps.lab3.process.comment;

import com.blps.lab3.databaseJPA.CommentStatus;
import com.blps.lab3.service.CommentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("updateCommentStatusDelegate")
public class UpdateCommentStatusDelegate implements JavaDelegate {

    @Autowired
    private CommentService commentService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer commentId = (Integer) execution.getVariable("commentId");
        
        // "status" parameter is set in BPMN Input/Output (APPROVED / REJECTED)
        // BUT, wait, in BPMN I used inputParameter name="status".
        // Let's check how to retrieve it. It should be a local variable.
        // OR we can infer it from "approved" boolean.
        
        // Let's use the explicit "status" variable if available (from Input Mapping)
        // If not, use logic.
        
        String statusStr = (String) execution.getVariable("status");
        
        CommentStatus status;
        if ("APPROVED".equals(statusStr)) {
            status = CommentStatus.APPROVED;
        } else if ("REJECTED".equals(statusStr)) {
            status = CommentStatus.REJECTED;
        } else {
            // Fallback to "approved" boolean from form
            Boolean approved = (Boolean) execution.getVariable("approved");
            status = (approved != null && approved) ? CommentStatus.APPROVED : CommentStatus.REJECTED;
        }

        commentService.updateCommentStatus(commentId, status);
    }
}
