package com.example.petclinic.repository;

import java.util.Collection;

import com.example.petclinic.model.Visits;
import com.example.petclinic.repositoryMock.VisitsRepositoryMock;
import com.example.petclinic.suppliers.VisitsSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VisitsRepositoryTest {
    
    @Mock
    private VisitsRepositoryMock visitsRepository;

    @BeforeAll
    static void prepareTests(){

    }

    @Test
    void shouldFindAll(){
        //when
        Mockito.when(this.visitsRepository.findAll()).thenReturn(VisitsSupplier.getVisits());
        //call
        Collection<Visits> visits = this.visitsRepository.findAll();
        //verify
        Assertions.assertNotNull(visits);
        Mockito.verify(this.visitsRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.visitsRepository.findById(Mockito.anyInt())).thenReturn(VisitsSupplier.getVisit());
        //call
        Visits visit = this.visitsRepository.findById(1).get();
        //verify
        Assertions.assertEquals(visit, this.visitsRepository.findById(1).get());
    }

    @Test
    void shouldSave(){
        Visits visit = new Visits();
        //when
        Mockito.when(this.visitsRepository.save(Mockito.any())).thenReturn(VisitsSupplier.getVisit().get());
        //call
        Visits newVisit = this.visitsRepository.save(visit);
        //verify
        Assertions.assertNotNull(newVisit);
    }

    @Test
    void shouldDelete(){
        //when
        Visits visit = VisitsSupplier.getVisit().get();
        //call
        this.visitsRepository.delete(visit);
        //verify
        Mockito.verify(this.visitsRepository).delete(Mockito.any());
    }

}
