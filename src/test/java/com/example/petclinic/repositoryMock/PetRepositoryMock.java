package com.example.petclinic.repositoryMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.suppliers.OwnerSupplier;
import com.example.petclinic.suppliers.PetTypeSupplier;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PetRepositoryMock implements PetRepository {

    @Override
    public List<Pet> findAll() {
        return Arrays.asList(
            new Pet().setPetId(1)
            .setName("testName")
          //  .setBirthDate(LocalDate.parse("2000-09-07"))
            .setOwner(OwnerSupplier.getOwner().get())
            .setType(PetTypeSupplier.getPetType().get()),
          //  .setVisits(VisitsSupplier.getVisit()),
            
            new Pet().setPetId(2)
            .setName("testName2")
        //    .setBirthDate(LocalDate.parse("2000-09-07"))
            .setOwner(OwnerSupplier.getOwner().get())
            .setType(PetTypeSupplier.getPetType().get()));
        //    .setVisits(VisitsSupplier.getVisit()));
    }

    @Override
    public List<Pet> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Pet> findAllById(Iterable<Integer> ids) {
        return null;
    }

    @Override
    public <S extends Pet> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Pet> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Pet> entities) {
        
    }

    @Override
    public void deleteAllInBatch() {
        
    }

    @Override
    public Pet getOne(Integer id) {
        return null;
    }

    @Override
    public <S extends Pet> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Pet> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Pet> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Pet> S save(S entity) {
        return null;
    }

    @Override
    public Optional<Pet> findById(Integer id) {
        return Optional.of(
            new Pet().setPetId(1)
            .setName("testName")
         //   .setBirthDate(LocalDate.parse("2000-09-07"))
            .setOwner(OwnerSupplier.getOwner().get())
            .setType(PetTypeSupplier.getPetType().get())
         //   .setVisits(VisitsSupplier.getVisit())
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
    public void delete(Pet entity) {
        
    }

    @Override
    public void deleteAll(Iterable<? extends Pet> entities) {
        
    }

    @Override
    public void deleteAll() {
        
    }

    @Override
    public <S extends Pet> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Pet> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Pet> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Pet> boolean exists(Example<S> example) {
        return false;
    }
    
}
