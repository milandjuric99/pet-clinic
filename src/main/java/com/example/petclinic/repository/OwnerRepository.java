package com.example.petclinic.repository;

import java.util.Collection;

import com.example.petclinic.model.Owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    
     Collection<Owner> findByLastName(String lastName);
}
