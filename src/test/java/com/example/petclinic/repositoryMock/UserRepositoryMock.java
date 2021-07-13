package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.User;
import com.example.petclinic.repository.UserRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UserRepositoryMock implements UserRepository {

    @Override
    public List<User> findAll() {
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

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<User> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public User getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.of(
            new User()
            .setId(1)
            .setUsername("testUsername")
            //.setRole("test-role")
            .setEnabled(true)
        );
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer id) {
        
    }

    @Override
    public void delete(User entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }
    
}
