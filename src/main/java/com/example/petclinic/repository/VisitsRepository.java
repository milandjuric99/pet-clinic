package com.example.petclinic.repository;

import java.util.Collection;

import com.example.petclinic.model.Visits;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer>{
    
    @Query("SELECT v FROM Visits v WHERE v.pet.petId = :petId")
    Collection<Visits> findByPetId(@Param("petId") Integer petId);
}
