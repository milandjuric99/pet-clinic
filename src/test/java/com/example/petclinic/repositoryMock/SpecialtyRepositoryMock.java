package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.repository.SpecialtyRepository;
import com.example.petclinic.suppliers.VetSupplier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SpecialtyRepositoryMock implements SpecialtyRepository {

    @Override
    public List<Specialty> findAll() {
        return Arrays.asList(
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
            .setVets(VetSupplier.getVetsSet()),
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
            .setVets(VetSupplier.getVetsSet())
        );
    }

    @Override
    public List<Specialty> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Specialty> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Specialty> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Specialty> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Specialty> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public Specialty getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends Specialty> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Specialty> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Specialty> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Specialty> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Specialty> findById(Integer id) {
        return Optional.of(
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
            .setVets(VetSupplier.getVetsSet()
            ));
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
    public void delete(Specialty entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends Specialty> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends Specialty> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Specialty> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Specialty> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Specialty> boolean exists(Example<S> example) {
        return false;
    }
    
}
