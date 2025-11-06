package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Objects.MoviesJPA;
import com.blps.lab3.databaseJPA.Objects.CommentJPA;
import com.blps.lab3.service.AccountsService;
import com.blps.lab3.service.CommentService;
import com.blps.lab3.service.MovieService;
import com.blps.lab3.service.OrderService;
import com.blps.lab3.utils.JwtUtil;
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
    public ResponseEntity<?> addComment(@PathVariable Integer movieID, HttpServletRequest request, @RequestBody CommentJPA comment) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String jwtToken = authorizationHeader.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);

        System.out.println(email);

        Optional<AccountsJPA> accountFound = accountsService.findAccountByEmail(email);

        if (accountFound.isPresent()) {
            commentService.addCommentToMovie(movieID, comment, accountFound.get());
            return ResponseEntity.status(HttpStatus.OK).body("Comment added to movie");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account Not Found");
        }
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
}
