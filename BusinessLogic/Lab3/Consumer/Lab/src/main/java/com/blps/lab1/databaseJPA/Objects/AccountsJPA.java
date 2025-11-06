package com.blps.lab1.databaseJPA.Objects;

import com.blps.lab1.databaseJPA.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "accounts", schema = "business_logic")
public class AccountsJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
