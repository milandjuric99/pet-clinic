package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Visits;
import com.example.petclinic.repository.VisitsRepository;
import com.example.petclinic.suppliers.PetSupplier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class VisitsRepositoryMock implements VisitsRepository {

    @Override
    public List<Visits> findAll() {
        return Arrays.asList(
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get()),
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get())
        );
    }

    @Override
    public List<Visits> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Visits> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Visits> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Visits> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Visits> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public Visits getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends Visits> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Visits> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Visits> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Visits> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Visits> findById(Integer id) {
        return Optional.of(
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get())
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
    public void delete(Visits entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends Visits> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends Visits> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Visits> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Visits> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Visits> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Collection<Visits> findByPetId(Integer petId) {
        return null;
    }
    
}
