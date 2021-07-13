package com.example.petclinic.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.User;
import com.example.petclinic.repositoryMock.UserRepositoryMock;
import com.example.petclinic.suppliers.UserSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepositoryMock userRepository;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.userRepository.findAll()).thenReturn(new ArrayList<>(UserSupplier.getUsers()));
        //call
        Collection<User> users = this.userRepository.findAll();
        //verify
        Assertions.assertNotNull(users);
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.userRepository.findById(Mockito.anyInt())).thenReturn(UserSupplier.getUser());
        //call
        User user = this.userRepository.findById(1).get();
        //verify
        Assertions.assertEquals(user, this.userRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        User user = new User();
        //when
        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(UserSupplier.getUser().get());
        //call
        User newUser = this.userRepository.save(user);
        //verify
        Assertions.assertNotNull(newUser);
    }

    @Test
    void shouldDelte(){
        //when
        User user = UserSupplier.getUser().get();
        //call
        this.userRepository.delete(user);
        //call
        Mockito.verify(this.userRepository).delete(Mockito.any());
    }
}
