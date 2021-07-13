package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.model.Vet;


public class SpecialtySupplier {
    

    public SpecialtySupplier() {
    }

    public static Set<Specialty> getSpecialtiesSet(){
        Set<Specialty> specialties = new HashSet<>();
        Set<Vet> vets = new HashSet<>();
        vets.add(
            new Vet()
            .setVetId(1)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
        );
        specialties.add(
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
            .setVets(vets)
        );
        return specialties;
    }

    public static Optional<Specialty> getSpecialty(){
        return Optional.of(
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
            .setVets(VetSupplier.getVetsSet()
            ));
        
    }

    public static List<Specialty> getSpecialties(){
        return Arrays.asList(
                new Specialty()
                .setSpecialtyId(1)
                .setName("testName")
                .setVets(VetSupplier.getVetsSet()),
                new Specialty()
                .setSpecialtyId(1)
                .setName("testName")
                .setVets(VetSupplier.getVetsSet())
            );
    }
}
