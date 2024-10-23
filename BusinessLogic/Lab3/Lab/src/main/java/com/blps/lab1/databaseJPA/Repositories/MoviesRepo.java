package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.MoviesJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepo extends JpaRepository<MoviesJPA, Integer> {

}
