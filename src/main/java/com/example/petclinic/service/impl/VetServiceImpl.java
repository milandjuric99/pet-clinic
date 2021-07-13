package com.example.petclinic.service.impl;

import java.util.Collection;

import com.example.petclinic.model.Vet;
import com.example.petclinic.repository.VetRepository;
import com.example.petclinic.service.VetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VetServiceImpl implements VetService{
    
    private VetRepository vetRepository;

    @Autowired
    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Collection<Vet> findAll() {
        return this.vetRepository.findAll();
    }

    @Override
    public Vet findById(Integer id) {
        return this.vetRepository.findById(id).get();
    }

    @Override
    public Vet save(Vet vet) {
        return this.vetRepository.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        this.vetRepository.delete(vet);
    }

}
