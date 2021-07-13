package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.User;

public interface UserService {

    Collection<User> findAll();
    User findById(Integer id);
    User findByUsername(String username);
    User save(User user);
    void delete(User user);
}
