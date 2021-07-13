package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Visits;

public interface VisitsService{
    
    Collection<Visits> findAllVisits();
    Collection<Visits> findAllVisitsByPetId(Integer petId);
    Visits findByVisitId(Integer id);
    Visits save(Visits visits);
    void delete(Visits visits);
}
