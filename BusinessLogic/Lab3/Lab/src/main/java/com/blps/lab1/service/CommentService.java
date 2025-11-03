package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.CommentStatus;
import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.CommentJPA;
import com.blps.lab1.databaseJPA.Repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public List<CommentJPA> getCommentsByMovieID(Integer movieID) {
        return commentRepo.findByMovieID(movieID);
    }

    public void addCommentToMovie(Integer movieID, CommentJPA comment, AccountsJPA account) {
        CommentJPA newComment = new CommentJPA();
        newComment.setContent(comment.getContent());
        newComment.setAuthor_id(account.getId());
        newComment.setMovie_id(movieID);
        newComment.setStatus(CommentStatus.UNDER_REVIEW);

        commentRepo.save(newComment);

        //Connect External API

    }

    public void deleteComment(Integer commentID) {
        commentRepo.deleteById(commentID);
    }
}
