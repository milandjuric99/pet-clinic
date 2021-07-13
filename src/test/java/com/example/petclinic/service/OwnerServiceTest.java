package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repository.OwnerRepository;
import com.example.petclinic.service.impl.OwnerServiceImpl;
import com.example.petclinic.suppliers.OwnerSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {
    
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFIndAll(){
        //when
        Mockito.when(this.ownerRepository.findAll()).thenReturn(new ArrayList<>(OwnerSupplier.getOwners()));
        //call
        Collection<Owner> owners = this.ownerService.findAllOwners();
        //verify
        Assertions.assertNotNull(owners);
        Mockito.verify(this.ownerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.ownerRepository.findById(Mockito.anyInt())).thenReturn(OwnerSupplier.getOwner());
        //call
        Owner owner = this.ownerService.findOwnerById(1);
        //verify
        Assertions.assertEquals(owner, this.ownerRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Owner owner = new Owner();
        //when
        Mockito.when(this.ownerRepository.save(Mockito.any())).thenReturn(OwnerSupplier.getOwner().get());
        //call
        Owner newOwner = this.ownerService.saveOwner(owner);
        //verify
        Assertions.assertNotNull(newOwner);
    }

    @Test
    void shouldDelete(){
        //when
        Owner owner = OwnerSupplier.getOwner().get();
        //call
        this.ownerService.deleteOwner(owner);
        //verify
        Mockito.verify(this.ownerRepository).delete(Mockito.any());
    }
}
