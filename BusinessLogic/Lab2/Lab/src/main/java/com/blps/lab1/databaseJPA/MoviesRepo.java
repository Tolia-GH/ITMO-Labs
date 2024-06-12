package com.blps.lab1.databaseJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesRepo extends JpaRepository<MoviesJPA, Integer> {

}
