package com.blps.lab1.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest { // Request for Authentication
    private String email;
    private String password;
    private String username;
}
