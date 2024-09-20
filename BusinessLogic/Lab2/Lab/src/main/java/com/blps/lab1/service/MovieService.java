package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.Objects.*;
import com.blps.lab1.databaseJPA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
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
    @Autowired
    private TicketsRepo ticketsRepo;
    @Autowired
    private OrdersRepo ordersRepo;

    public List<MoviesJPA> getAllMovies() {
        return moviesRepo.findAll();
    }

    public Optional<MoviesJPA> getMovie(@PathVariable Integer id) {
        return moviesRepo.findById(id);
    }

    @Transactional
    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        System.out.println(movie.getId());
        System.out.println(movie.getName());
        System.out.println(movie.getDescription());
        System.out.println(movie.getRating());
        System.out.println(movie.getRateCount());
        return moviesRepo.save(movie);
    }

    @Transactional
    public String deleteMovie(Integer movieID) {
        moviesRepo.deleteById(movieID);
        return "Movie deleted!";
    }

    @Transactional
    public FavouritesJPA addToFavourites(Integer movieID, AccountsJPA account) {
        FavouritesJPA newFavourite = new FavouritesJPA();
        newFavourite.setMovie_id(movieID);
        newFavourite.setUser_id(account.getId());
        return favouritesRepo.save(newFavourite);
    }

    public List<ReviewsJPA> getReviewsByMovieID(Integer movieID) {
        return reviewsRepo.findByMovieID(movieID);
    }

    @Transactional
    public ReviewsJPA addReviewToMovie(Integer movieID, ReviewsJPA review) {
        ReviewsJPA newReview = new ReviewsJPA();
        newReview.setContent(review.getContent());
        newReview.setAuthor_id(review.getAuthor_id());
        newReview.setMovie_id(movieID);
        return reviewsRepo.save(newReview);
    }


    @Transactional
    public void deleteReview(Integer reviewID) {
        reviewsRepo.deleteById(reviewID);
    }

    public FavouritesJPA findFavouritesByMovieIdAndAccountID(Integer movieID, Integer accountID) {
        return favouritesRepo.findByMovieIDAndUser_id(movieID, accountID);
    }

    public Optional<TicketsJPA> getTicketByMovieID(Integer movieID) {
        return ticketsRepo.findByMovieID(movieID);
    }

    @Transactional
    public TicketsJPA addTicketToMovie(Integer movieID, TicketsJPA ticket) {
        TicketsJPA newTicket = new TicketsJPA();
        newTicket.setMovie_id(movieID);
        newTicket.setAmount(ticket.getAmount());
        newTicket.setPrice(ticket.getPrice());
        return ticketsRepo.save(newTicket);
    }

    @Transactional
    public void buyTicket(Integer ticketID, OrdersJPA order) {
        OrdersJPA newOrder = new OrdersJPA();
        newOrder.setUser_id(order.getUser_id());
        newOrder.setTicket_id(ticketID);
        newOrder.setIs_paid(false);
        ordersRepo.save(newOrder);
    }

    @Transactional
    public List<TicketsJPA> getTickets() {
        return ticketsRepo.findAll();
    }

    @Transactional
    public Optional<ReviewsJPA> getReviewByID(Integer reviewID) {
        return reviewsRepo.findById(reviewID);
    }
}
