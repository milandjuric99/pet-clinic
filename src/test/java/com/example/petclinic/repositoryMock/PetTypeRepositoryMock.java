package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.PetType;
import com.example.petclinic.repository.PetTypeRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PetTypeRepositoryMock implements PetTypeRepository{

    @Override
    public List<PetType> findAll() {
        return Arrays.asList(
            new PetType()
            .setTypeId(1)
            .setName("testType1"),
    
            new PetType()
            .setTypeId(2)
            .setName("testType2")
            );
    }

    @Override
    public List<PetType> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<PetType> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends PetType> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends PetType> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<PetType> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public PetType getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends PetType> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PetType> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<PetType> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PetType> S save(S entity) {
        return null;
    }

    @Override
    public Optional<PetType> findById(Integer id) {
        return Optional.of(
            new PetType()
            .setTypeId(1)
            .setName("testName1")
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
    public void delete(PetType entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends PetType> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends PetType> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PetType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PetType> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PetType> boolean exists(Example<S> example) {
        return false;
    }
    
}
