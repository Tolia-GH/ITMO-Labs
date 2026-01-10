package com.blps.lab3.service;

import com.blps.lab3.databaseJPA.Objects.*;
import com.blps.lab3.databaseJPA.OrderStatus;
import com.blps.lab3.databaseJPA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MoviesRepo moviesRepo;
    @Autowired
    private CommentRepo commentRepo;
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

    public MoviesJPA addMovie(@RequestBody MoviesJPA movie) {
        System.out.println(movie.getId());
        System.out.println(movie.getName());
        System.out.println(movie.getDescription());
        System.out.println(movie.getRating());
        System.out.println(movie.getRateCount());
        return moviesRepo.save(movie);
    }

    public String deleteMovie(Integer movieID) {
        moviesRepo.deleteById(movieID);
        return "Movie deleted!";
    }

    public FavouritesJPA addToFavourites(Integer movieID, AccountsJPA account) {
        FavouritesJPA newFavourite = new FavouritesJPA();
        newFavourite.setMovie_id(movieID);
        newFavourite.setUser_id(account.getId());
        return favouritesRepo.save(newFavourite);
    }

    public FavouritesJPA findFavouritesByMovieIdAndAccountID(Integer movieID, Integer accountID) {
        return favouritesRepo.findByMovieIDAndUser_id(movieID, accountID);
    }

    public Optional<TicketsJPA> getTicketByMovieID(Integer movieID) {
        return ticketsRepo.findByMovieID(movieID);
    }

    public TicketsJPA addTicketToMovie(Integer movieID, TicketsJPA ticket) {
        TicketsJPA newTicket = new TicketsJPA();
        newTicket.setMovie_id(movieID);
        newTicket.setAmount(ticket.getAmount());
        newTicket.setPrice(ticket.getPrice());
        return ticketsRepo.save(newTicket);
    }

    public OrdersJPA buyTicket(Integer ticketID, AccountsJPA account) {
        OrdersJPA newOrder = new OrdersJPA();
        newOrder.setUser_id(account.getId());
        newOrder.setTicket_id(ticketID);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setCreation_time(LocalDateTime.now());
        return ordersRepo.save(newOrder);
    }

    public List<TicketsJPA> getTickets() {
        return ticketsRepo.findAll();
    }

    public Optional<CommentJPA> getCommentByID(Integer reviewID) {
        return commentRepo.findById(reviewID);
    }

}
