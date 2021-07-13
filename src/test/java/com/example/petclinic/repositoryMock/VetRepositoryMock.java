package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Vet;
import com.example.petclinic.repository.VetRepository;
import com.example.petclinic.suppliers.SpecialtySupplier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class VetRepositoryMock implements VetRepository {

    @Override
    public List<Vet> findAll() {
        return Arrays.asList(
            new Vet()
            .setVetId(1)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
            .setSpecialties(SpecialtySupplier.getSpecialtiesSet()),
            new Vet()
            .setVetId(2)
            .setFirstName("testFirstName2")
            .setLastName("testLastName2")
            .setSpecialties(SpecialtySupplier.getSpecialtiesSet())
        );
    }

    @Override
    public List<Vet> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Vet> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Vet> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Vet> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Vet> entities) {
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public Vet getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends Vet> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Vet> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Vet> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Vet> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Vet> findById(Integer id) {
        return Optional.of(
        new Vet()
        .setVetId(1)
        .setFirstName("testFirstName")
        .setLastName("testLastName")
        .setSpecialties(SpecialtySupplier.getSpecialtiesSet()));
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
    public void delete(Vet entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends Vet> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends Vet> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Vet> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Vet> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Vet> boolean exists(Example<S> example) {
        return false;
    }
    
}
