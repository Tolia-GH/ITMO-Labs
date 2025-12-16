package com.blps.lab3.databaseJPA.Repositories;

import com.blps.lab3.databaseJPA.Objects.TicketsJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketsRepo extends JpaRepository<TicketsJPA, Integer> {

    @Query(value = "select A from TicketsJPA A where A.movie_id = ?1")
    Optional<TicketsJPA> findByMovieID(Integer movieID);
}
