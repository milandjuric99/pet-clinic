package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Vet;

public interface VetService {
    
    Collection<Vet> findAll();
    Vet findById(Integer id);
    Vet save(Vet vet);
    void delete(Vet vet);
}
