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

    public List<CommentJPA> getReviewsByMovieID(Integer movieID) {
        return commentRepo.findByMovieID(movieID);
    }

    public void addReviewToMovie(Integer movieID, CommentJPA review, AccountsJPA account) {
        CommentJPA newReview = new CommentJPA();
        newReview.setContent(review.getContent());
        newReview.setAuthor_id(account.getId());
        newReview.setMovie_id(movieID);
        newReview.setStatus(CommentStatus.UNDER_REVIEW);

        //Connect External API

        commentRepo.save(newReview);
    }

    public void deleteReview(Integer reviewID) {
        commentRepo.deleteById(reviewID);
    }
}
