package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.MoviesJPA;
import com.blps.lab1.databaseJPA.MoviesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MoviesRepo moviesRepo;

    public List<MoviesJPA> getAllMovies() {
        return moviesRepo.findAll();
    }

    public MoviesJPA addMovie(MoviesJPA movie) {
        return moviesRepo.save(movie);
    }

    public void deleteMovie(Integer id) {
        moviesRepo.deleteById(id);
    }
}
