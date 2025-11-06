package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.CommentJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<CommentJPA, Integer>{

    @Query(value = "select A from CommentJPA A where A.movie_id = ?1")
    List<CommentJPA> findByMovieID(Integer movieID);
}
