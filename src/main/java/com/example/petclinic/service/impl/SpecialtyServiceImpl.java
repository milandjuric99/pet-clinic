package com.example.petclinic.service.impl;

import java.util.Collection;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.repository.SpecialtyRepository;
import com.example.petclinic.service.SpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    
    private SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Collection<Specialty> findAll() {
        return this.specialtyRepository.findAll();
    }

    @Override
    public Specialty findById(Integer id) {
        return this.specialtyRepository.findById(id).get();
    }

    @Override
    public Specialty save(Specialty specialty) {
        return this.specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Specialty specialty) {
        this.specialtyRepository.delete(specialty);
    }
}
