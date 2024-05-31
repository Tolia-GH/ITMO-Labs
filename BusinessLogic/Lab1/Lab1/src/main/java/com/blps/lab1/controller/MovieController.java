package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.MoviesJPA;
import com.blps.lab1.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MoviesJPA> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieByID(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @PostMapping
    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        return movieService.addMovie(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovieByID(@PathVariable Integer id) {
        movieService.deleteMovie(id);
    }
}
