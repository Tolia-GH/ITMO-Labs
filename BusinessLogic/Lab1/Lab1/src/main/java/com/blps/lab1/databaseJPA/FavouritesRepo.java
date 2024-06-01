package com.blps.lab1.databaseJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouritesRepo extends JpaRepository<FavouritesJPA, Integer> {
    @Query(value = "select A from FavouritesJPA A where A.movie_id = ?1")
    FavouritesJPA findByMovieID(Integer movieID);
}
