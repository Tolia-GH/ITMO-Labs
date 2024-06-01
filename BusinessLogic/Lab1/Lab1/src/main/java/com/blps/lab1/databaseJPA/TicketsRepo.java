package com.blps.lab1.databaseJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsRepo extends JpaRepository<TicketsJPA, Integer> {

    @Query(value = "select A from TicketsJPA A where A.movie_id = ?1")
    List<TicketsJPA> findByMovieID(Integer movieID);
}
