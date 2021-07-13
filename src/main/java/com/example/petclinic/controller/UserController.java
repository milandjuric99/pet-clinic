package com.example.petclinic.controller;

import java.util.Collection;

import com.example.petclinic.model.User;
import com.example.petclinic.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/admin/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<User>> getUsers(){

        Collection<User> users = this.userService.findAll();
        if(users == null){
            return new ResponseEntity<Collection<User>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId){

        User user = this.userService.findById(userId);
        if(user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){

        User user = this.userService.findByUsername(username);
        if(user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        if(user.getId() != null){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.userService.save(user);
        headers.setLocation(ucBuilder.path("/api/users/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Integer userId, @RequestBody User user,
    UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = this.userService.findById(userId) == null ||
        userId.equals(user.getId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User currentUser = this.userService.findById(userId);
        if(currentUser == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        currentUser.setUsername(user.getPassword());
        currentUser.setPassword(user.getPassword());
        currentUser.setEnabled(user.getEnabled());
        currentUser.setRoles(user.getRoles());
        this.userService.save(currentUser);
        headers.setLocation(ucBuilder.path("/api/users/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(currentUser, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Integer userId){

        User user = this.userService.findById(userId);
        if(user == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.userService.delete(user);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    
}
