package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Specialty;

public interface SpecialtyService {
    
    Collection<Specialty> findAll();
    Specialty findById(Integer id);
    Specialty save(Specialty specialty);
    void delete(Specialty specialty); 
}
