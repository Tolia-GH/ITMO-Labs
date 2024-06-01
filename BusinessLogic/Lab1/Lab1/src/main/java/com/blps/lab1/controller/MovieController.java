package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.*;
import com.blps.lab1.service.MovieService;
import com.blps.lab1.service.OrderService;
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

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersRepo ordersRepo;

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
    public ReviewsJPA addReview(@PathVariable Integer movieID, @RequestBody ReviewsJPA review) {
        return movieService.addReviewToMovie(movieID, review);
    }

    @DeleteMapping("/{movieID}/review/{reviewID}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer movieID, @PathVariable Integer reviewID) {
        movieService.deleteReview(reviewID);
        return ResponseEntity.ok("Review deleted");
    }

    @PostMapping("/{movieID}/ticket")
    public TicketsJPA addTicket(@PathVariable Integer movieID, @RequestBody TicketsJPA ticket) {
        return movieService.addTicketToMovie(movieID, ticket);
    }

    @GetMapping("/{movieID}/ticket")
    public List<TicketsJPA> getTickets(@PathVariable Integer movieID) {
        return movieService.getTicketsByMovieID(movieID);
    }

    @PostMapping("/{movieID}/ticket/buy")
    public ResponseEntity<?> buyTicket(@PathVariable Integer movieID, @RequestBody OrdersJPA order) {
        movieService.buyTicket(movieID, order);
        return ResponseEntity.ok("Ticket wait for payment");
    }

    @PutMapping("/{movieID}/ticket/payment/{orderID}")
    public ResponseEntity<?> payTicket(@PathVariable Integer movieID, @PathVariable Integer orderID) {
        orderService.payOrder(orderID);
        return ResponseEntity.ok("Ticket is paid!");
    }
}
