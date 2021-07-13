package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.User;

public class UserSupplier {
    
    public UserSupplier() {
    }

    public static Optional<User> getUser(){
        return Optional.of(
            new User()
            .setId(1)
            .setUsername("testUsername")
            //.setRole("test-role")
            .setEnabled(true)
        );
    }

    public static List<User> getUsers(){
        return Arrays.asList(
            new User()
            .setId(1)
            .setUsername("testUsername")
            .setEnabled(true),
            new User()
            .setId(2)
            .setUsername("testUsername2")
            .setEnabled(true)
        );
    }
}
