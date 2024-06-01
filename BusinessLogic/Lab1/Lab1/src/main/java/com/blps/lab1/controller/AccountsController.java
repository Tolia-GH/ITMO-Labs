package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.MoviesJPA;
import com.blps.lab1.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    @GetMapping
    public List<AccountsJPA> getAllAccounts() {
        return accountsService.findAllAccounts();
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> login(@RequestBody AccountsJPA account) {
        AccountsJPA accountFound = accountsService.findAccountByEmail(account.getEmail());
        if (accountFound.getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok("Successful Login!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or Password Wrong!");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody AccountsJPA account) {
        AccountsJPA accountFound = accountsService.findAccountByEmail(account.getEmail());
        if (accountFound == null) {
            accountsService.addAccount(account);
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email Already Exists!");
        }
    }

    @GetMapping("/order")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok("");
    }

    @PostMapping("/order/{orderID}")
    public ResponseEntity<?> payOrder(@PathVariable Integer orderID) {
        return ResponseEntity.ok("");
    }

    @GetMapping("/favourite")
    public List<MoviesJPA> getFavouriteMovies() {
        return new ArrayList<>();
    }
}
