package com.blps.lab1.service;

import com.blps.lab1.databaseJPA.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;
}
