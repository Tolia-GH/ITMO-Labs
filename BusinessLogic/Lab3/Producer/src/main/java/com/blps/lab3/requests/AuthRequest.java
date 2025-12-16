package com.blps.lab3.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest { // Request for Authentication
    private String email;
    private String password;
    private String username;
}
