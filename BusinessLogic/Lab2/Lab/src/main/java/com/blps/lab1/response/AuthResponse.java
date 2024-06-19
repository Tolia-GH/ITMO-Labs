package com.blps.lab1.response;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String jwt;

    public AuthResponse (String jwt) {
        this.jwt = jwt;
    }
}
