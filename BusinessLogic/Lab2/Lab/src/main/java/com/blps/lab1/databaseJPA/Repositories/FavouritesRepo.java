package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.FavouritesJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepo extends JpaRepository<FavouritesJPA, Integer> {
    @Query(value = "select A from FavouritesJPA A where A.movie_id = ?1 and A.user_id = ?2")
    FavouritesJPA findByMovieIDAndUser_id(Integer movieID, Integer accountID);

    @Query(value = "select A from FavouritesJPA A where A.user_id = ?1")
    List<FavouritesJPA> findAllByAccountID(Integer accountID);
}
