package com.blps.lab3.process.comment;

import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.service.CommentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("createExternalTaskDelegate")
public class CreateExternalTaskDelegate implements JavaDelegate {

    @Autowired
    private CommentService commentService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer commentId = (Integer) execution.getVariable("commentId");
        
        CommentJPA comment = commentService.getCommentById(commentId);
        Integer externalTaskId = commentService.createReviewTaskInClickUp(comment);
        
        commentService.updateCommentExternalTaskId(commentId, externalTaskId);
        execution.setVariable("externalTaskId", externalTaskId);
    }
}
