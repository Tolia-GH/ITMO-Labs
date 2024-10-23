package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepo extends JpaRepository<AccountsJPA, Integer> {

//    @Query(value = "select A from AccountsJPA A where A.email = ?1")
//    AccountsJPA findByEmail(String email);

    Optional<AccountsJPA> findByEmail(String email);
}
