package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Vet;
import com.example.petclinic.repository.VetRepository;
import com.example.petclinic.service.impl.VetServiceImpl;
import com.example.petclinic.suppliers.VetSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VetServiceTest {
    
    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetServiceImpl vetService;

    @BeforeAll
    static void prepareTest(){

    }

    @Test
    void shouldFIndAll(){
        //when
        Mockito.when(this.vetRepository.findAll()).thenReturn(new ArrayList<>(VetSupplier.getVets()));
        //call
        Collection<Vet> vets = this.vetService.findAll();
        //verify
        Assertions.assertNotNull(vets);
        Mockito.verify(this.vetRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.vetRepository.findById(Mockito.anyInt())).thenReturn(VetSupplier.getVet());       
        //call
        Vet vet = this.vetService.findById(1);
        //verify
        Assertions.assertEquals(vet, this.vetRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Vet vet = new Vet();
        //when
        Mockito.when(this.vetRepository.save(Mockito.any())).thenReturn(VetSupplier.getVet().get());
        //call
        Vet newVet = this.vetService.save(vet);
        //verify
        Assertions.assertNotNull(newVet);
    }

    @Test
    void shouldDelete(){
        //when
       Vet vet = VetSupplier.getVet().get();
        //call
        this.vetService.delete(vet);
        //verify
        Mockito.verify(this.vetRepository).delete(vet);
    }
}
