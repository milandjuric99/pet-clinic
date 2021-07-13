package com.example.petclinic.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Vet;
import com.example.petclinic.repositoryMock.VetRepositoryMock;
import com.example.petclinic.suppliers.VetSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VetRepositoryTest {

    @Mock
    private VetRepositoryMock vetRepository;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.vetRepository.findAll()).thenReturn(new ArrayList<>(VetSupplier.getVets()));
        //call
        Collection<Vet> vets = this.vetRepository.findAll();
        //verify
        Assertions.assertNotNull(vets);
        Mockito.verify(this.vetRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.vetRepository.findById(Mockito.anyInt())).thenReturn(VetSupplier.getVet());
        //call
        Vet vet = this.vetRepository.findById(1).get();
        //when
        Assertions.assertEquals(vet, this.vetRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Vet vet = new Vet();
        //when
        Mockito.when(this.vetRepository.save(Mockito.any())).thenReturn(VetSupplier.getVet().get());
        //call
        Vet newVet = this.vetRepository.save(vet);
        //verify
        Assertions.assertNotNull(newVet);
    }

    @Test
    void shouldDelete(){
        //when
        Vet vet = VetSupplier.getVet().get();
        //call
        this.vetRepository.delete(vet);
        //verify
        Mockito.verify(this.vetRepository).delete(Mockito.any());
    }
    
}
