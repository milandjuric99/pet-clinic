package com.example.petclinic.repository;

import com.example.petclinic.model.Visits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer>{
    
}
