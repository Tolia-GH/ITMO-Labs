package com.blps.lab3.controller;

import com.blps.lab3.databaseJPA.Objects.AccountsJPA;
import com.blps.lab3.databaseJPA.Repositories.AccountsRepo;
import com.blps.lab3.requests.AuthRequest;
import com.blps.lab3.response.AuthResponse;
import com.blps.lab3.service.AccountsDetailService;
import com.blps.lab3.utils.JwtUtil;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private IdentityService identityService;


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
            // Save original password for Camunda
            String originalPassword = account.getPassword();
            
            // 1. Save to Business DB (Encrypted)
            account.setPassword(new BCryptPasswordEncoder().encode(originalPassword));
            accountsRepo.save(account);

            // 2. Sync to Camunda Identity Service
            try {
                // Create Camunda User (using email as userId)
                User camundaUser = identityService.newUser(account.getEmail());
                camundaUser.setPassword(originalPassword); // Camunda needs plain text for its own login form
                camundaUser.setFirstName(account.getUsername());
                camundaUser.setLastName("Lab4");
                camundaUser.setEmail(account.getEmail());
                identityService.saveUser(camundaUser);

                // Add user to group
                if (account.getRole().toString().equals("ADMIN")) {
                    identityService.createMembership(account.getEmail(), "camunda-admin");
                } else if (account.getRole().toString().equals("USER")) {
                    identityService.createMembership(account.getEmail(), "USER");
                }


            } catch (Exception e) {
                // Log error but don't fail registration? Or rollback?
                // For lab simplicity, we log. Ideally, this should be transactional or eventually consistent.
                e.printStackTrace();
            }

            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email Already Exists!");
        }
    }

    @GetMapping("/test-jms")
    public void testJms() {
        jmsTemplate.convertAndSend("Consumer.mail.VirtualTopic.order.payment",
                "{\"orderId\":123,\"userEmail\":\"test@qq.com\"}");
    }
}
