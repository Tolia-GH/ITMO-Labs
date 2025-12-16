package com.blps.lab3.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse { // Response for Authentication
    private String jwt; // to store JWT token
}
