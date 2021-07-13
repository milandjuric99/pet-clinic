package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.PetType;

public class PetTypeSupplier {
    

    public PetTypeSupplier() {
    }

    public static Optional<PetType> getPetType(){
        return Optional.of(new PetType()
        .setTypeId(1)
        .setName("testType"));
    }

    public static List<PetType> getPetTypes(){
        return Arrays.asList(
        new PetType()
        .setTypeId(1)
        .setName("testType1"),

        new PetType()
        .setTypeId(2)
        .setName("testType2")
        );
    }
}
