package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.PetType;
import com.example.petclinic.repository.PetTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PetTypeServiceImpl implements PetTypeService {

    private PetTypeRepository petTypeRepository;

    @Autowired
    public PetTypeServiceImpl(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }


    @Override
    public PetType findPetTypeById(Integer petTypeId) {
        PetType petType = null;
        petType = this.petTypeRepository.findById(petTypeId).get();
        
        return petType;
    }

    @Override
    public Collection<PetType> findAllPetTypes() {
        return this.petTypeRepository.findAll();
    }

    @Override
    public void savePetType(PetType petType) {
        this.petTypeRepository.save(petType);
        
    }

    @Override
    public void deletePetType(PetType petType) {
        this.petTypeRepository.delete(petType);
    }
    
}
