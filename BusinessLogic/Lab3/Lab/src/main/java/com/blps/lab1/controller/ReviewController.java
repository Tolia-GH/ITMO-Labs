package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.MoviesJPA;
import com.blps.lab1.databaseJPA.Objects.ReviewsJPA;
import com.blps.lab1.service.AccountsService;
import com.blps.lab1.service.MovieService;
import com.blps.lab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class ReviewController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    AccountsService accountsService;
    @GetMapping("/{movieID}/review")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<ReviewsJPA> getReviews(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return null;
        } else {
            return movieService.getReviewsByMovieID(movieID);
        }
    }

    @PostMapping("/{movieID}/review")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ReviewsJPA addReview(@PathVariable Integer movieID, @RequestBody ReviewsJPA review) {
        return movieService.addReviewToMovie(movieID, review);
    }

    @DeleteMapping("/{movieID}/review/{reviewID}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteReview(@PathVariable Integer movieID, @PathVariable Integer reviewID) {
        Optional<MoviesJPA> movieFound = movieService.getMovie(movieID);
        Optional<ReviewsJPA> reviewFound = movieService.getReviewByID(reviewID);
        if (movieFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found!");
        } else if (reviewFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Review not found!");
        } else {
            movieService.deleteReview(reviewID);
            return ResponseEntity.ok("Review deleted");
        }
    }
}
