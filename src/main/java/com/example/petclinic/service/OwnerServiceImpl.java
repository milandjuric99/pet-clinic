package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repository.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner findOwnerById(Integer id) {
        Owner owner = null;
            owner = this.ownerRepository.findById(id).get();
        return owner;
    }

    @Override
    public Collection<Owner> findAllOwners() {
        return this.ownerRepository.findAll();
    }

    @Override
    public void saveOwner(Owner owner) {
        this.ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Owner owner) {
        this.ownerRepository.delete(owner);
    }

    @Override
    public Collection<Owner> findOwnerByLastName(String lastName) {
        return this.ownerRepository.findByLastName(lastName);
    }

}
