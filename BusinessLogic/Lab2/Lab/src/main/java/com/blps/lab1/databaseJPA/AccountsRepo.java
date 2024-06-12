package com.blps.lab1.databaseJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepo extends JpaRepository<AccountsJPA, Integer> {

    @Query(value = "select A from AccountsJPA A where A.email = ?1")
    AccountsJPA findByEmail(String email);
}
