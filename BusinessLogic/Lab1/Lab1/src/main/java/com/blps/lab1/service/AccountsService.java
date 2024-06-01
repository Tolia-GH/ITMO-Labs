package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AccountsService {
    @Autowired
    private AccountsRepo accountsRepo;

    public List<AccountsJPA> findAllAccounts() {
        return accountsRepo.findAll();
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
}
