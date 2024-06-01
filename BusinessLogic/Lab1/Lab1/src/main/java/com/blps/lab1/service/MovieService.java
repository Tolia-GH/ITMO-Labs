package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MoviesRepo moviesRepo;
    @Autowired
    private ReviewsRepo reviewsRepo;
    @Autowired
    private FavouritesRepo favouritesRepo;

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

    public void deleteMovie(Integer movieID) {
        moviesRepo.deleteById(movieID);
    }

    public FavouritesJPA addToFavourites(Integer movieID, AccountsJPA account) {


        FavouritesJPA newFavourite = new FavouritesJPA();
        newFavourite.setMovie_id(movieID);
        newFavourite.setUser_id(account.getId());
        return favouritesRepo.save(newFavourite);
    }

    public List<ReviewsJPA> getReviewsByMovieID(Integer movieID) {
        return reviewsRepo.findByMovieId(movieID);
    }

    public ReviewsJPA addReviewToMovie(Integer movieID, HttpServletRequest request) {
        ReviewsJPA newReview = new ReviewsJPA();
        newReview.setContent(request.getParameter("content"));
        newReview.setMovie_id(movieID);
        return reviewsRepo.save(newReview);
    }


    public void deleteReview(Integer reviewID) {
        reviewsRepo.deleteById(reviewID);
    }

    public FavouritesJPA findFavouritesByMovieId(Integer movieID) {
        return favouritesRepo.findByMovieId(movieID);
    }
}
