package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.MoviesJPA;
import com.blps.lab1.databaseJPA.MoviesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MoviesRepo moviesRepo;

    public List<MoviesJPA> getAllMovies() {
        return moviesRepo.findAll();
    }

    public Optional<MoviesJPA> getMovie(@PathVariable Integer id) {
        return moviesRepo.findById(id);
    }

    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        System.out.println(movie.getId());
        System.out.println(movie.getName());
        System.out.println(movie.getDescription());
        System.out.println(movie.getRating());
        System.out.println(movie.getRateCount());
        return moviesRepo.save(movie);
    }

    public void deleteMovie(Integer id) {
        moviesRepo.deleteById(id);
    }
}
