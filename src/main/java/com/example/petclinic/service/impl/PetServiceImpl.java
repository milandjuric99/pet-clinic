package com.example.petclinic.service.impl;

import java.util.Collection;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @Override
    public Pet findPetById(Integer id) {
        return this.petRepository.findById(id).get();
    }

    @Override
    public Collection<Pet> findAllPets() {
        return this.petRepository.findAll();
    }

    @Override
    public Pet savePet(Pet pet) {
        return this.petRepository.save(pet);
    }

    @Override
    public void deletePet(Pet pet) {
        this.petRepository.delete(pet);
    }
    
}
