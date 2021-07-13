package com.example.petclinic.suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petclinic.model.Visits;


public class VisitsSupplier {

    public VisitsSupplier() {
    }

    public static Optional<Visits> getVisit(){
        return Optional.of(
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get())
        );
    }

    public static List<Visits> getVisits(){
        return Arrays.asList(
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get()),
            new Visits()
            .setVisitId(1)
            .setDescription("description")
            .setPet(PetSupplier.getPet().get())
        );
    }
}