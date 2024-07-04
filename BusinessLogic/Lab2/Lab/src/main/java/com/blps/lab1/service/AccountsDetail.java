package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.AccountsJPA;
import com.blps.lab1.databaseJPA.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountsDetail implements UserDetails {
    private AccountsJPA account;

    private List<String> permissions; // the permission list get from AccountsDetailService

    @JsonIgnore // necessary to ignore serialization when running
    private List<SimpleGrantedAuthority> authorities;

    public AccountsDetail(AccountsJPA account, List<String> permissions) {
        this.account = account;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // get authorities from permissions
        if (authorities == null) {// more efficient by making this judgement
            authorities = permissions.stream()
                    .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission))
                    .collect(Collectors.toList());
        }

        return authorities;

        //return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    public String getEmail() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
