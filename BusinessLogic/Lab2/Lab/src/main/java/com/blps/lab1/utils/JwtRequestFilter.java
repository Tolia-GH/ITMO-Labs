package com.blps.lab1.utils;

import com.blps.lab1.service.AccountsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter { // OncePerRequestFilter: Each request will go through filter only once
    @Autowired
    private AccountsDetailService accountsDetailService; // to load user by account's email
    @Autowired
    private JwtUtil jwtUtil; // for generating, analyzing and authenticating JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // to get JWT from Http request header of "Authorization"

        String username = null;
        String jwt = null;
        String email = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { // to check whether the Http request header starts with "Bearer ", which is a standard format of JWT
            jwt = authHeader.substring(7); // Take substring after 'Bearer '(7 symbols) and get jwt
            username = jwtUtil.extractUsername(jwt); // from jwt get username
            email = jwtUtil.extractEmail(jwt); // from jwt get email
            role = jwtUtil.extractRole(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // when username is not null and there is no authentication info in Security context
            UserDetails userDetails = this.accountsDetailService.loadUserByUsername(email); // load user's detail info by using accountsDetailService

            if (jwtUtil.validateToken(jwt, userDetails)) { // check whether JWT token is expired, if not, make user authenticated
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.singletonList(new SimpleGrantedAuthority(role))
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
