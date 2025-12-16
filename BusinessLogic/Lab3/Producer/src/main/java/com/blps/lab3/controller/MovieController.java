package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.Objects.*;
import com.blps.lab3.service.AccountsService;
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
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    private JwtUtil jwtUtil;

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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA = movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.deleteMovie(movieID));
        }
    }

    @GetMapping("/{movieID}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMovieByID(@PathVariable Integer movieID) {
        Optional<MoviesJPA> moviesJPA = movieService.getMovie(movieID);
        if (moviesJPA.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie not found");
        } else {
            return ResponseEntity.ok(movieService.getMovie(movieID));
        }
    }

    @PostMapping("/{movieID}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addToFavourites(@PathVariable Integer movieID, HttpServletRequest request) {
        Optional<MoviesJPA> movie = movieService.getMovie(movieID);

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
        }

        String jwtToken = authorizationHeader.substring(7);
        String email = jwtUtil.extractEmail(jwtToken);

        System.out.println(email);

        Optional<AccountsJPA> accountFound = accountsService.findAccountByEmail(email);

        if (accountFound.isPresent()) {
            FavouritesJPA favouriteFound = movieService.findFavouritesByMovieIdAndAccountID(movieID, accountFound.get().getId());

            System.out.println(movie);
            if (favouriteFound != null && movie.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie Already Added to Favourites!");
            } else if (movie.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie does not exists!");
            } else {
                return ResponseEntity.ok(movieService.addToFavourites(movieID, accountFound.get()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account Not Found");
        }

    }
}

