package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.AccountsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountsDetailService implements UserDetailsService {
    @Autowired
    private AccountsRepo accountsRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountsJPA account = accountsRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new AccountsDetail(account);
    }
}
