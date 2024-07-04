package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Objects.FavouritesJPA;
import com.blps.lab1.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountsService accountsService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AccountsJPA> getAllAccounts() {
        return accountsService.findAllAccounts();
    }

    @GetMapping("/{accountID}/favourites")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<FavouritesJPA> getFavouriteMoviesByAccount(@PathVariable Integer accountID) {
        return accountsService.getFavouritesByAccountID(accountID);
    }
}
