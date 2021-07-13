package com.example.petclinic.repository;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.repositoryMock.SpecialtyRepositoryMock;
import com.example.petclinic.suppliers.SpecialtySupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SpecialtyRepositoryTest {
    
    @Mock
    private SpecialtyRepositoryMock specialtyRepository;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.specialtyRepository.findAll()).thenReturn(new ArrayList<>(SpecialtySupplier.getSpecialties()));
        //call
        Collection<Specialty> specialties = this.specialtyRepository.findAll();
        //verify
        Assertions.assertNotNull(specialties);
        Mockito.verify(this.specialtyRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.specialtyRepository.findById(Mockito.anyInt())).thenReturn(SpecialtySupplier.getSpecialty());
        //call
        Specialty specialty = this.specialtyRepository.findById(1).get();
        //verify
        Assertions.assertSame(specialty, this.specialtyRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Specialty specialty = new Specialty();
        //when
        Mockito.when(this.specialtyRepository.save(Mockito.any())).thenReturn(SpecialtySupplier.getSpecialty().get());
        //call
        Specialty newSpecialty = this.specialtyRepository.save(specialty);
        //verify
        Assertions.assertNotNull(newSpecialty);
    }

    @Test
    void shouldDelete(){
        //when
        Specialty specialty = SpecialtySupplier.getSpecialty().get();
        //call
        this.specialtyRepository.delete(specialty);
        //verify
        Mockito.verify(this.specialtyRepository).delete(Mockito.any());
    }
}
