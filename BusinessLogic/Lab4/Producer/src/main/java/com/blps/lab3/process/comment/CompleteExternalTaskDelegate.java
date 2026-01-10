package com.blps.lab3.process.comment;

import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.service.CommentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("completeExternalTaskDelegate")
public class CompleteExternalTaskDelegate implements JavaDelegate {

    @Autowired
    private CommentService commentService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer commentId = (Integer) execution.getVariable("commentId");
        
        CommentJPA comment = commentService.getCommentById(commentId);
        
        if (comment.getRelated_task_id() != null) {
            commentService.updateReviewTaskInClickUp(comment);
        }
    }
}
