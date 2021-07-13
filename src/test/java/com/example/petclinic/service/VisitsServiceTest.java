package com.example.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import com.example.petclinic.model.Visits;
import com.example.petclinic.repository.VisitsRepository;
import com.example.petclinic.service.impl.VisitsServiceImpl;
import com.example.petclinic.suppliers.VisitsSupplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VisitsServiceTest {
    
    @Mock
    private VisitsRepository visitsRepository;

    @InjectMocks
    private VisitsServiceImpl visitService;

    @BeforeAll
    static void prepareTest(){

    }

    @Test
    void shouldFIndAll(){
        //when
        Mockito.when(this.visitsRepository.findAll()).thenReturn(new ArrayList<>(VisitsSupplier.getVisits()));
        //call
        Collection<Visits> visits= this.visitService.findAllVisits();
        //verify
        Assertions.assertNotNull(visits);
        Mockito.verify(this.visitsRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldFindById(){
        //when
        Mockito.when(this.visitsRepository.findById(Mockito.anyInt())).thenReturn(VisitsSupplier.getVisit());       
        //call
        Visits visit = this.visitService.findByVisitId(1);
        //verify
        Assertions.assertEquals(visit, this.visitsRepository.findById(1).get());
    }

    @Test
    void shouldCreate(){
        Visits visit  = new Visits();
        //when
        Mockito.when(this.visitsRepository.save(Mockito.any())).thenReturn(VisitsSupplier.getVisit().get());
        //call
        Visits newVisit = this.visitService.save(visit);
        //verify
        Assertions.assertNotNull(newVisit);
    }

    @Test
    void shouldDelete(){
        //when
        Visits visit = VisitsSupplier.getVisit().get();
        //call
        this.visitService.delete(visit);
        //verify
        Mockito.verify(this.visitsRepository).delete(visit);
    }
}
