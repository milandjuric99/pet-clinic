package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.PetType;

public interface PetTypeService {
    
    PetType findPetTypeById(Integer petTypeId);
	Collection<PetType> findAllPetTypes();
	PetType savePetType(PetType petType);
	void deletePetType(PetType petType);
}
