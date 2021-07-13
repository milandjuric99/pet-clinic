package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Owner;

public class OwnerSupplier {
    

    public OwnerSupplier() {
    }
    
    public static Optional<Owner> getOwner(){
        return Optional.of(new Owner()
        .setOwnerId(1)
        .setFirstName("testName")
        .setLastName("testLastName")
        .setCity("testCity")
        .setTelephone("4567"));
    }

    public static List<Owner> getOwners(){
        return Arrays.asList(
        new Owner()
        .setOwnerId(1)
        .setFirstName("testName1")
        .setLastName("testLastName1")
        .setCity("testCity1")
        .setTelephone("4567"),

        new Owner()
        .setOwnerId(2)
        .setFirstName("testName2")
        .setLastName("testLastName2")
        .setCity("testCity2")
        .setTelephone("7654")
        );
    }
}
