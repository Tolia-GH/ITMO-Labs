package com.example.demo_back.service;

import com.example.demo_back.JPAdatabase.AccountJpa;
import com.example.demo_back.JPAdatabase.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public void addAccount(String username,String password){
        AccountJpa accountJpa = new AccountJpa();
        accountJpa.setUsername(username);
        accountJpa.setPassword(password);
        accountRepository.save(accountJpa);
    }

   public List<AccountJpa> findAccountByName(String username){
        return accountRepository.findByUsername(username);}
}
