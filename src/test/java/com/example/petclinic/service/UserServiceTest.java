package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.User;
import com.example.petclinic.repository.UserRepository;
import com.example.petclinic.service.impl.UserServiceImpl;
import com.example.petclinic.suppliers.UserSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void prepareTest(){

    }

    @Test
    void shouldFIndAll(){
        //when
        Mockito.when(this.userRepository.findAll()).thenReturn(new ArrayList<>(UserSupplier.getUsers()));
        //call
        Collection<User> users = this.userService.findAll();
        //verify
        Assertions.assertNotNull(users);
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(UserSupplier.getUser());       
        //call
        User user = this.userService.findById(1);
        //verify
        Assertions.assertEquals(user, this.userRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        User user = new User();
        //when
        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(UserSupplier.getUser().get());
        //call
        User newUser = this.userService.save(user);
        //verify
        Assertions.assertNotNull(newUser);
    }

    @Test
    void shouldDelete(){
        //when
        User user = UserSupplier.getUser().get();
        //call
        this.userService.delete(user);
        //verify
        Mockito.verify(this.userRepository).delete(user);
    }
}
