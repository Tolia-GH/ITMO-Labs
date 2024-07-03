package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.*;
import com.blps.lab1.service.AccountsService;
import com.blps.lab1.service.MovieService;
import com.blps.lab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    AccountsService accountsService;
    @Autowired
    private ReviewsRepo reviewsRepo;
    @GetMapping
    public List<MoviesJPA> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        return movieService.addMovie(movie);
    }

    @DeleteMapping("/{movieID}")
    public ResponseEntity<?> deleteMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.deleteMovie(movieID));
        }
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<?> getMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.getMovie(movieID));
        }
    }

    @PostMapping("/{movieID}")
    public ResponseEntity<?> addToFavourites(@PathVariable Integer movieID, @RequestBody AccountsJPA account) {
        Optional<MoviesJPA> movie = movieService.getMovie(movieID);
        Optional<AccountsJPA> accountFound = accountsService.findAccountByID(account.getId());
        FavouritesJPA favouriteFound = movieService.findFavouritesByMovieIdAndAccountID(movieID, account.getId());

        System.out.println(movie);
        if (favouriteFound != null && movie.isPresent() && accountFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Already Added to Favourites!");
        } else if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie does not exists!");
        } else if (accountFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exists!");
        } else {
            return ResponseEntity.ok(movieService.addToFavourites(movieID, account));
        }
    }

    @GetMapping("/{movieID}/review")
    public List<ReviewsJPA> getReviews(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return null;
        } else {
            return movieService.getReviewsByMovieID(movieID);
        }
    }

    @PostMapping("/{movieID}/review")
    public ReviewsJPA addReview(@PathVariable Integer movieID, @RequestBody ReviewsJPA review) {
        return movieService.addReviewToMovie(movieID, review);
    }

    @DeleteMapping("/{movieID}/review/{reviewID}")
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

    @PostMapping("/{movieID}/ticket")
    public ResponseEntity<?> addTicket(@PathVariable Integer movieID, @RequestBody TicketsJPA ticket) {
        Optional<TicketsJPA> ticketFound = movieService.getTicketByMovieID(movieID);
        if (ticketFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket of this movie already exists");
        }
        return ResponseEntity.ok(movieService.addTicketToMovie(movieID, ticket));
    }

    @GetMapping("/ticket")
    public List<TicketsJPA> getTickets() {
        return movieService.getTickets();
    }

    @PostMapping("/{movieID}/ticket/buy")
    public ResponseEntity<?> buyTicket(@PathVariable Integer movieID, @RequestBody OrdersJPA order) {
        Optional<AccountsJPA> account = accountsService.findAccountByID(order.getUser_id());
        Optional<TicketsJPA> ticket = movieService.getTicketByMovieID(movieID);
        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account does not exists!");
        } else if (ticket.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket does not exists!");
        } else {
            movieService.buyTicket(ticket.get().getId(), order);
            return ResponseEntity.ok("Ticket wait for payment");
        }
    }

    @PutMapping("/{movieID}/ticket/payment/{orderID}")
    public ResponseEntity<?> payTicket(@PathVariable Integer movieID, @PathVariable Integer orderID) {
        Optional<OrdersJPA> orderFound = orderService.getOrderByID(orderID);
        if (orderFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not found!");
        }
        orderService.payOrder(orderID);
        return ResponseEntity.ok("Ticket is paid!");
    }
}
