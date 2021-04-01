package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.PetType;

public interface PetTypeService {
    
    PetType findPetTypeById(Integer petTypeId);
	Collection<PetType> findAllPetTypes();
	void savePetType(PetType petType);
	void deletePetType(PetType petType);
}
