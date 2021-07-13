package com.example.petclinic.service.impl;

import java.util.Collection;

import com.example.petclinic.model.Visits;
import com.example.petclinic.repository.VisitsRepository;
import com.example.petclinic.service.VisitsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitsServiceImpl implements VisitsService{

    private VisitsRepository visitsRepository;

    @Autowired
    public VisitsServiceImpl(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }


    @Override
    public Collection<Visits> findAllVisits() {
        return this.visitsRepository.findAll();
    }

    @Override
    public Collection<Visits> findAllVisitsByPetId(Integer petId) {
        return this.visitsRepository.findByPetId(petId);
    }

    @Override
    public Visits findByVisitId(Integer id) {
        return this.visitsRepository.findById(id).get();
    }

    @Override
    public Visits save(Visits visits) {
        return this.visitsRepository.save(visits);
    }

    @Override
    public void delete(Visits visits) {
        this.visitsRepository.delete(visits);
    }
    
}
