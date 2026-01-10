package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.CommentStatus;
import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.MoviesJPA;
import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.CommentService;
import com.blps.lab3.service.MovieService;
import com.blps.lab3.service.OrderService;
import com.blps.lab3.utils.JwtUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class CommentController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AccountsService accountsService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/{movieID}/comment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<CommentJPA> getComment(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return null;
        } else {
            return commentService.getCommentsByMovieID(movieID);
        }
    }

    @PostMapping("/{movieID}/comment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    // Note: RequestBody is ignored if we just start process, but maybe user wants to pass content via API
    // If so, we can pass it as variables. 
    // BUT, since we added a User Task "Write comment" at the start,
    // the process should ideally just start, and then user goes to Tasklist to write.
    // However, to support API-based submission too, we can pre-fill the form variable if provided.
    public ResponseEntity<?> addComment(@PathVariable Integer movieID, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String jwtToken = authorizationHeader.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);
        
        // Start Camunda Process
        // We set initiator variable so the process knows who started it
        java.util.Map<String, Object> variables = new java.util.HashMap<>();
        variables.put("initiator", email); // Used by PersistDelegate to find user
        variables.put("userEmail", email);
        // We can pre-set movieId so user doesn't have to type it in Tasklist if we want
        // But the form has a field for it. Let's pre-fill it if we can (requires form to read process var).
        // Camunda forms read process variables by default if keys match.
        variables.put("movieId", movieID);
        
        runtimeService.startProcessInstanceByKey("CommentModerationProcess", variables);

        return ResponseEntity.ok("Comment process started. Please go to Tasklist to write your comment.");
    }

    @DeleteMapping("/{movieID}/comment/{commentID}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable Integer movieID, @PathVariable Integer commentID) {
        Optional<MoviesJPA> movieFound = movieService.getMovie(movieID);
        Optional<CommentJPA> commentFound = movieService.getCommentByID(commentID);
        if (movieFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found!");
        } else if (commentFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment not found!");
        } else {
            commentService.deleteComment(commentID);
            return ResponseEntity.ok("Comment deleted");
        }
    }

    @PutMapping("/comment/{commentID}/review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> reviewComment(@PathVariable Integer commentID, @RequestParam("status") String statusValue) {
        try {
            CommentStatus status = CommentStatus.valueOf(statusValue);

            CommentJPA updatedComment = commentService.reviewComment(commentID, status);
            return ResponseEntity.ok("Comment review updated status to " + updatedComment.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
