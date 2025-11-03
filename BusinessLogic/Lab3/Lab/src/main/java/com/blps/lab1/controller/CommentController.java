package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.FavouritesJPA;
import com.blps.lab1.databaseJPA.Objects.MoviesJPA;
import com.blps.lab1.databaseJPA.Objects.CommentJPA;
import com.blps.lab1.service.AccountsService;
import com.blps.lab1.service.CommentService;
import com.blps.lab1.service.MovieService;
import com.blps.lab1.service.OrderService;
import com.blps.lab1.utils.JwtUtil;
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
    @GetMapping("/{movieID}/review")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<CommentJPA> getReviews(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return null;
        } else {
            return commentService.getReviewsByMovieID(movieID);
        }
    }

    @PostMapping("/{movieID}/review")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addReview(@PathVariable Integer movieID, HttpServletRequest request, @RequestBody CommentJPA review) {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String jwtToken = authorizationHeader.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);

        System.out.println(email);

        Optional<AccountsJPA> accountFound = accountsService.findAccountByEmail(email);

        if (accountFound.isPresent()) {
            commentService.addReviewToMovie(movieID, review, accountFound.get());
            return ResponseEntity.status(HttpStatus.OK).body("Review added to movie");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account Not Found");
        }
    }

    @DeleteMapping("/{movieID}/review/{reviewID}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReview(@PathVariable Integer movieID, @PathVariable Integer reviewID) {
        Optional<MoviesJPA> movieFound = movieService.getMovie(movieID);
        Optional<CommentJPA> reviewFound = movieService.getReviewByID(reviewID);
        if (movieFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found!");
        } else if (reviewFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review not found!");
        } else {
            commentService.deleteReview(reviewID);
            return ResponseEntity.ok("Review deleted");
        }
    }
}
