package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repository.OwnerRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OwnerRepositoryMock implements OwnerRepository{

    @Override
    public List<Owner> findAll() {
        return Arrays.asList(
            new Owner()
            .setOwnerId(1)
            .setFirstName("testName1")
            .setLastName("testLastName1")
            .setCity("testCity1")
            .setTelephone("4567"),
             new Owner()
            .setOwnerId(2)
            .setFirstName("testName2")
            .setLastName("testLastName2")
            .setCity("testCity2")
            .setTelephone("7654")
        );
    }

    @Override
    public List<Owner> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Owner> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Owner> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Owner> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public Owner getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Owner> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Owner> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Owner> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Owner> findById(Integer id) {
        return Optional.of(new Owner()
        .setOwnerId(1)
        .setFirstName("testName1")
        .setLastName("testLastName1")
        .setCity("testCity1")
        .setTelephone("4567")
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
    public void delete(Owner entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends Owner> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends Owner> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Owner> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Owner> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Owner> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Collection<Owner> findByLastName(String lastName) {
        return Arrays.asList(
            new Owner()
            .setOwnerId(1)
            .setFirstName("testName1")
            .setLastName("testLastName1")
            .setCity("testCity1")
            .setTelephone("4567"),
            new Owner()
            .setOwnerId(2)
            .setFirstName("testName2")
            .setLastName("testLastName1")
            .setCity("testCity2")
            .setTelephone("4567")
        ); 
    }
    
}
