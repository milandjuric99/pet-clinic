package com.example.petclinic.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.PetType;
import com.example.petclinic.repositoryMock.PetTypeRepositoryMock;
import com.example.petclinic.suppliers.PetTypeSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetTypeRepositoryTest {
    
    @Mock
    private PetTypeRepositoryMock petTypeRepository;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.petTypeRepository.findAll()).thenReturn(new ArrayList<>(PetTypeSupplier.getPetTypes()));
        //call
        Collection<PetType> types = this.petTypeRepository.findAll();
        //verify
        Assertions.assertNotNull(types);
        Mockito.verify(this.petTypeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.petTypeRepository.findById(Mockito.anyInt())).thenReturn(PetTypeSupplier.getPetType());
        //call
        PetType type = this.petTypeRepository.findById(1).get();
        //verify
        Assertions.assertEquals(type, this.petTypeRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        PetType type = new PetType();
        //when
        Mockito.when(this.petTypeRepository.save(Mockito.any())).thenReturn(PetTypeSupplier.getPetType().get());
        //call
        PetType newType = this.petTypeRepository.save(type);
        //verify
        Assertions.assertNotNull(newType);
    }

    @Test
    void shouldDelete(){
        //when
        PetType type = PetTypeSupplier.getPetType().get();
        //call
        this.petTypeRepository.delete(type);
        //verify
        Mockito.verify(this.petTypeRepository).delete(Mockito.any());
    }
}
