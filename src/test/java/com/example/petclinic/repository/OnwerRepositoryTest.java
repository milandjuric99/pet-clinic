package com.example.petclinic.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repositoryMock.OwnerRepositoryMock;
import com.example.petclinic.suppliers.OwnerSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OnwerRepositoryTest {

    @Mock
    private OwnerRepositoryMock ownerRepository;

    @BeforeAll
    static void prepareTests() {

    }

    @Test
    void shouldFindAll() {
        // when
        Mockito.when(this.ownerRepository.findAll()).thenReturn(new ArrayList<>(OwnerSupplier.getOwners()));

        // call
        Collection<Owner> owners = this.ownerRepository.findAll();

        // verify
        Assertions.assertNotNull(owners);
        Mockito.verify(this.ownerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById() {

        //when
        Mockito.when(this.ownerRepository.findById(Mockito.anyInt()))
            .thenReturn(OwnerSupplier.getOwner());

        //call
        Owner owner = this.ownerRepository.findById(1).get();

        //verify
        Assertions.assertSame(owner, this.ownerRepository.findById(1).get());
    }

    @Test
    void shouldCreate() {

        Owner owner = new Owner();
        //when
        Mockito.when(this.ownerRepository.save(owner))
            .thenReturn(OwnerSupplier.getOwner().get());
        //call
        Owner newOwner = this.ownerRepository.save(owner);
        //verify
        Assertions.assertNotNull(newOwner);

    }

    @Test
    void shouldDelete() {
        //when
        Owner owner = OwnerSupplier.getOwner().get();
        //call
        this.ownerRepository.delete(owner);
        //verify
        Mockito.verify(this.ownerRepository).delete(Mockito.any());
    }

}
