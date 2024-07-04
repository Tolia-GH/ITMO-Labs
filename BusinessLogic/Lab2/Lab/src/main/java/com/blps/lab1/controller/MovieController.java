package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.*;
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
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    AccountsService accountsService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<MoviesJPA> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        return movieService.addMovie(movie);
    }

    @DeleteMapping("/{movieID}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.deleteMovie(movieID));
        }
    }

    @GetMapping("/{movieID}")
    @PreAuthorize("hasRole('ROLE_ADMIN' or hasRole('ROLE_USER'))")
    public ResponseEntity<?> getMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA =movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.getMovie(movieID));
        }
    }

    @PostMapping("/{movieID}")
    @PreAuthorize("hasRole('ROLE_USER' or hasRole('ROLE_ADMIN'))")
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
}
