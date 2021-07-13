package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.PetType;
import com.example.petclinic.repository.PetTypeRepository;
import com.example.petclinic.service.impl.PetTypeServiceImpl;
import com.example.petclinic.suppliers.PetTypeSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetTypeServiceTest {
    
    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private PetTypeServiceImpl petTypeService;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFIndAll(){
        //when
        Mockito.when(this.petTypeRepository.findAll()).thenReturn(new ArrayList<>(PetTypeSupplier.getPetTypes()));
        //call
        Collection<PetType> petTypes = this.petTypeService.findAllPetTypes();
        //verify
        Assertions.assertNotNull(petTypes);
        Mockito.verify(this.petTypeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.petTypeRepository.findById(Mockito.anyInt())).thenReturn(PetTypeSupplier.getPetType());
        //call
        PetType petType = this.petTypeService.findPetTypeById(1);
        //verify
        Assertions.assertEquals(petType, this.petTypeRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        PetType petType = new PetType();
        //when
        Mockito.when(this.petTypeRepository.save(Mockito.any())).thenReturn(PetTypeSupplier.getPetType().get());
        //call
        PetType newPetType = this.petTypeService.savePetType(petType);
        //verify
        Assertions.assertNotNull(newPetType);
    }

    @Test
    void shouldDelete(){
        //when
        PetType petType = PetTypeSupplier.getPetType().get();
        //call
        this.petTypeService.deletePetType(petType);
        //verify
        Mockito.verify(this.petTypeRepository).delete(petType);
    }
}
