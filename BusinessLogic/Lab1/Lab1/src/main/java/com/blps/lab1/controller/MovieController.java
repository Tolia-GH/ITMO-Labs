package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.FavouritesJPA;
import com.blps.lab1.databaseJPA.MoviesJPA;
import com.blps.lab1.databaseJPA.ReviewsJPA;
import com.blps.lab1.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MoviesJPA> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        return movieService.addMovie(movie);
    }

    @DeleteMapping("/{movieID}")
    public void deleteMovieByID(@PathVariable Integer movieID) {
        movieService.deleteMovie(movieID);
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<?> getMovieByID(@PathVariable Integer movieID) {
        return ResponseEntity.ok(movieService.getMovie(movieID));
    }

    @PostMapping("/{movieID}")
    public ResponseEntity<?> addToFavourites(@PathVariable Integer movieID, @RequestBody AccountsJPA account) {
        FavouritesJPA favouriteFound = movieService.findFavouritesByMovieId(movieID);
        if (favouriteFound == null) {
            return ResponseEntity.ok(movieService.addToFavourites(movieID, account));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Already Added to Favourites!");
        }
    }

    @GetMapping("/{movieID}/review")
    public List<ReviewsJPA> getReviews(@PathVariable Integer movieID) {
        return movieService.getReviewsByMovieID(movieID);
    }

    @PostMapping("/{movieID}/review")
    public ReviewsJPA addReview(@PathVariable Integer movieID, @RequestBody HttpServletRequest request) {
        return movieService.addReviewToMovie(movieID, request);
    }

    @DeleteMapping("/{movieID}/review/{reviewID}")
    public void deleteReview(@PathVariable Integer movieID, @PathVariable Integer reviewID) {
        movieService.deleteReview(reviewID);
    }

    @PostMapping("/{movieID}/ticket")
    public ResponseEntity<?> addTicket(@PathVariable Integer movieID) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/{movieID}/ticket")
    public ResponseEntity<?> getTickets(@PathVariable Integer movieID) {
        return ResponseEntity.ok("");
    }

    @PostMapping("/{movieID}/ticket/buy")
    public ResponseEntity<?> buyTicket(@PathVariable Integer movieID) {
        return ResponseEntity.ok("");
    }
}
