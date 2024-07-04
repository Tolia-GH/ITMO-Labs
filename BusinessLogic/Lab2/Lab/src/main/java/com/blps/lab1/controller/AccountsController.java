package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.FavouritesJPA;
import com.blps.lab1.databaseJPA.OrdersJPA;
import com.blps.lab1.service.AccountsService;
import com.blps.lab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    @GetMapping("/account")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountsJPA> getAllAccounts() {
        return accountsService.findAllAccounts();
    }

//    @PostMapping("/signIn")
//    public ResponseEntity<?> signIn(@RequestBody AccountsJPA account) {
//        AccountsJPA accountFound = accountsService.findAccountByEmail(account.getEmail());
//        if (accountFound == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account not found!");
//        } else if (accountFound.getPassword().equals(account.getPassword())) {
//            return ResponseEntity.ok("Successful Login!");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or Password Wrong!");
//        }
//    }
//
//    @PostMapping("/signUp")
//    public ResponseEntity<?> signUp(@RequestBody AccountsJPA account) {
//        AccountsJPA accountFound = accountsService.findAccountByEmail(account.getEmail());
//        if (accountFound == null) {
//            accountsService.addAccount(account);
//            return ResponseEntity.ok(account);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email Already Exists!");
//        }
//    }

    @GetMapping("/account/{accountID}/favourites")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<FavouritesJPA> getFavouriteMovies(@PathVariable Integer accountID) {
        return accountsService.getFavouritesByAccountID(accountID);
    }

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrdersJPA> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/account/{accountID}/order")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrdersJPA> getAllOrdersByAccountID(@PathVariable Integer accountID) {
        return orderService.getAllOrdersByAccountID(accountID);
    }

    @PutMapping("/account/{accountID}/order/{orderID}/payment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> payTicket(@PathVariable Integer accountID, @PathVariable Integer orderID) {
        orderService.payOrder(orderID);
        return ResponseEntity.ok("Ticket is paid!");
    }
}
