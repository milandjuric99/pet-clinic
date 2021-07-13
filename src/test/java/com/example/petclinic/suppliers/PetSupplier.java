package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Pet;



public class PetSupplier {


    public PetSupplier() {
    }

    public static Optional<Pet> getPet(){
        return Optional.of(new Pet()
        .setPetId(1)
        .setName("testName")
     //   .setBirthDate(LocalDate.parse("2000-09-07"))
        .setOwner(OwnerSupplier.getOwner().get())
        .setType(PetTypeSupplier.getPetType().get()));
     //   .setVisits(VisitsSupplier.getVisit());
    }

    public static List<Pet> getPets(){
        return Arrays.asList(
            new Pet().setPetId(1)
            .setName("testName")
          //  .setBirthDate(LocalDate.parse("2000-09-07"))
            .setOwner(OwnerSupplier.getOwner().get())
            .setType(PetTypeSupplier.getPetType().get()),
          //  .setVisits(VisitsSupplier.getVisit()),
            
            new Pet().setPetId(2)
            .setName("testName2")
          //  .setBirthDate(LocalDate.parse("2000-09-07"))
            .setOwner(OwnerSupplier.getOwner().get())
            .setType(PetTypeSupplier.getPetType().get()));
          //  .setVisits(VisitsSupplier.getVisit()));
    }
}
