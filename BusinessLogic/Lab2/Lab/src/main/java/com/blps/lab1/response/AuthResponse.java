package com.blps.lab1.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse { // Response for Authentication
    private String jwt; // to store JWT token
}
