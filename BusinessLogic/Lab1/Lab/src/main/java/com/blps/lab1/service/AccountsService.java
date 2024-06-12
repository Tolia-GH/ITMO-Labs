package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private FavouritesRepo favouritesRepo;

    public List<AccountsJPA> findAllAccounts() {
        return accountsRepo.findAll();
    }

    public Optional<AccountsJPA> findAccountByID(Integer id) {
        return accountsRepo.findById(id);
    }
    public AccountsJPA findAccountByEmail(String email) {
        return accountsRepo.findByEmail(email);
    }

    public AccountsJPA addAccount(@RequestBody AccountsJPA account) {
        System.out.println(account.getId());
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());
        return accountsRepo.save(account);
    }

    public List<FavouritesJPA> getFavouritesByAccountID(Integer accountID) {
        return favouritesRepo.findAllByAccountID(accountID);
    }
}
