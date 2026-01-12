package com.blps.lab3.service;

import com.blps.lab3.databaseJPA.CommentStatus;
import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.databaseJPA.Repositories.CommentRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    @Value("${external.url}")
    private String url;
    @Value("${external.token}")
    private String token;
    @Value("${external.list.id}")
    private String listId;
    @Value("${external.space.id}")
    private String spaceId;

    @Autowired
    private CommentRepo commentRepo;

    private ObjectMapper mapper = new ObjectMapper();

    public List<CommentJPA> getCommentsByMovieID(Integer movieID) {
        return commentRepo.findByMovieID(movieID);
    }

    public CommentJPA persistComment(String content, Integer movieID, Integer userId) {
        CommentJPA newComment = new CommentJPA();
        newComment.setContent(content);
        newComment.setAuthor_id(userId);
        newComment.setMovie_id(movieID);
        newComment.setStatus(CommentStatus.UNDER_REVIEW);
        return commentRepo.save(newComment);
    }

    public void updateCommentExternalTaskId(Integer commentId, Integer taskId) {
        CommentJPA comment = commentRepo.findById(commentId).orElseThrow();
        comment.setRelated_task_id(taskId);
        commentRepo.save(comment);
    }

    public void addCommentToMovie(Integer movieID, CommentJPA comment, AccountsJPA account) {
        CommentJPA newComment = new CommentJPA();
        newComment.setContent(comment.getContent());
        newComment.setAuthor_id(account.getId());
        newComment.setMovie_id(movieID);
        newComment.setStatus(CommentStatus.UNDER_REVIEW);

        commentRepo.save(newComment);

        //Connect External API
        Integer task_id = createReviewTaskInClickUp(newComment);
        newComment.setRelated_task_id(task_id);

        commentRepo.save(newComment);
    }

    public Integer createReviewTaskInClickUp(CommentJPA comment) {
        String url = this.url + "tm/tasks/";

        System.out.println("Sending create task request to url: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json");

        // Clean content to avoid JSON breaking issues
        String safeContent = "";
        if (comment.getContent() != null) {
            safeContent = comment.getContent()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", " ")
                    .replace("\r", " ");
        }

        String taskBody = String.format(
                "{" +
                        "\"title\": \"Review Comment#%d\"," +
                        "\"description\": \"Comment#%d Content: %s\"," +
                        "\"locations\": [{\"projectId\": 2}]" +
                "}", comment.getId(), comment.getId(), safeContent
        );

        HttpEntity<String> entity = new HttpEntity<>(taskBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode responseJson = mapper.readTree(response.getBody());
                Integer taskId = responseJson.path("task").path("id").asInt();
                System.out.println("Review comment task created with id: " + taskId);
                return taskId;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse JSON response", e);
            }
        } else {
            System.out.println("Failed creating review comment task on ClickUp with error: " + response.getStatusCode());
            throw new RuntimeException("Failed creating review comment task with error: " + response.getStatusCode());
        }
    }

    public void deleteComment(Integer commentID) {
        commentRepo.deleteById(commentID);
    }

    public CommentJPA getCommentById(Integer id) {
        return commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void updateCommentStatus(Integer commentId, CommentStatus status) {
        CommentJPA comment = getCommentById(commentId);
        comment.setStatus(status);
        commentRepo.save(comment);
    }

    public CommentJPA reviewComment(Integer commentID, CommentStatus status) {
        CommentJPA comment = commentRepo.findById(commentID)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setStatus(status);

        updateReviewTaskInClickUp(comment);
        return commentRepo.save(comment);
    }

    public void updateReviewTaskInClickUp(CommentJPA comment) {

        String url = this.url + "tm/tasks/" + comment.getRelated_task_id() + "/complete";

        System.out.println("Sending complete task request to url: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Review comment task updated!");
        } else {
            System.out.println("Failed updating review task with error: " + response.getStatusCode());
        }
    }
}
