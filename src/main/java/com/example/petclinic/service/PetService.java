package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Pet;

public interface PetService {
    
    Pet findPetById(Integer id);
	Collection<Pet> findAllPets();
	Pet savePet(Pet pet);
	void deletePet(Pet pet);
}
