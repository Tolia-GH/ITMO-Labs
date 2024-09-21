package com.blps.lab1.controller;

import com.blps.lab1.databaseJPA.Objects.AccountsJPA;
import com.blps.lab1.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab1.requests.AuthRequest;
import com.blps.lab1.response.AuthResponse;
import com.blps.lab1.service.AccountsDetailService;
import com.blps.lab1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AccountsRepo accountsRepo;
    @Autowired
    private AccountsDetailService accountsDetailService;


    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            final UserDetails userDetails = accountsDetailService.loadUserByUsername(authRequest.getEmail()); // use email to load user
            final String jwt = jwtUtil.generateToken(userDetails); // generate Jwt
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or Password Error!");
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody AccountsJPA account) {
        AccountsJPA accountFound = accountsRepo.findByEmail(account.getEmail()).orElse(null);
        if (accountFound == null) {
            account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
            accountsRepo.save(account);
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email Already Exists!");
        }
    }
}
