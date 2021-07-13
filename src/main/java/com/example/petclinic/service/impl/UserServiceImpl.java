package com.example.petclinic.service.impl;

import java.util.Collection;

import com.example.petclinic.model.User;
import com.example.petclinic.repository.UserRepository;
import com.example.petclinic.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Collection<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }

}
