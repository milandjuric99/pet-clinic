package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.model.Vet;

public class VetSupplier {
    

    public VetSupplier() {
    }

    public static Set<Vet> getVetsSet(){
        Set<Vet> vets  = new HashSet<>();
        Set<Specialty> specialties = new HashSet<>();
        specialties.add(
            new Specialty()
            .setSpecialtyId(1)
            .setName("testName")
        );
        vets.add(
            new Vet()
            .setVetId(1)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
            .setSpecialties(specialties)
        );
        return vets;
    }

    public static Optional<Vet> getVet(){
        return Optional.of(
            new Vet()
            .setVetId(1)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
            .setSpecialties(SpecialtySupplier.getSpecialtiesSet())
        );
    }

    public static List<Vet> getVets(){
        return Arrays.asList(
            new Vet()
            .setVetId(1)
            .setFirstName("testFirstName")
            .setLastName("testLastName")
            .setSpecialties(SpecialtySupplier.getSpecialtiesSet()),
            new Vet()
            .setVetId(2)
            .setFirstName("testFirstName2")
            .setLastName("testLastName2")
            .setSpecialties(SpecialtySupplier.getSpecialtiesSet())
        );
    }
}
