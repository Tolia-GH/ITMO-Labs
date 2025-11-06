package com.blps.lab3.service;

import com.blps.lab3.databaseJPA.CommentStatus;
import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.databaseJPA.Repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    @Value("${clickup.url}")
    private String clickUpUrl;
    @Value("${clickup.api.token}")
    private String clickUpApiToken;
    @Value("${clickup.list.id}")
    private String listId;
    @Value("${clickup.space.id}")
    private String spaceId;

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
        createReviewTaskInClickUp(newComment);
    }

    private void createReviewTaskInClickUp(CommentJPA comment) {
        String url = clickUpUrl + "list/" + listId + "/task/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", clickUpApiToken);
        headers.set("Content-Type", "application/json");
        headers.set("accept", "application/json");

        String taskBody = String.format(
                "{" +
                        "\"name\": \"Review Comment#%d\"," +
                        "\"description\": \"Comment#%d Content: %s\"" +
                "}", comment.getId(), comment.getId(), comment.getContent()
        );

        HttpEntity<String> entity = new HttpEntity<>(taskBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Review comment task created on ClickUp!");
        } else {
            System.out.println("Failed creating review comment task on ClickUp with error: " + response.getStatusCode());
        }
    }

    public void deleteComment(Integer commentID) {
        commentRepo.deleteById(commentID);
    }

    public CommentJPA reviewComment(Integer commentID, CommentStatus status) {
        CommentJPA comment = commentRepo.findById(commentID)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setStatus(status);
        return commentRepo.save(comment);
    }
}
