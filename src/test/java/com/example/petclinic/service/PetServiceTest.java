package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.service.impl.PetServiceImpl;
import com.example.petclinic.suppliers.PetSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    
    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.petRepository.findAll()).thenReturn(new ArrayList<>(PetSupplier.getPets()));
        //call
        Collection<Pet> pets = this.petService.findAllPets();
        //verify
        Assertions.assertNotNull(pets);
        Mockito.verify(this.petRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.petRepository.findById(Mockito.anyInt())).thenReturn(PetSupplier.getPet());
        //call
        Pet pet = this.petService.findPetById(1);
        //verify
        Assertions.assertEquals(pet, this.petRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Pet pet = new Pet();
        //when
        Mockito.when(this.petRepository.save(Mockito.any())).thenReturn(PetSupplier.getPet().get());
        //call
        Pet newPet = this.petService.savePet(pet);
        //verify
        Assertions.assertNotNull(newPet);
    }

    @Test
    void shouldDelete(){
        //when
        Pet pet = PetSupplier.getPet().get();
        //call
        this.petService.deletePet(pet);
        //verify
        Mockito.verify(this.petRepository).delete(pet);
    }
}
