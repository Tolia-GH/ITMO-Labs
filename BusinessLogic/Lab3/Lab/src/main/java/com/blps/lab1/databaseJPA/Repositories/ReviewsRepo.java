package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.ReviewsJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepo extends JpaRepository<ReviewsJPA, Integer>{

    @Query(value = "select A from ReviewsJPA A where A.movie_id = ?1")
    List<ReviewsJPA> findByMovieID(Integer movieID);
}
