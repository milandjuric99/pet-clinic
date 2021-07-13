package com.example.petclinic.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "specialties")
public class Specialty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer specialtyId;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "specialties")
    private Set<Vet> vets;

    public Specialty() {
    }


    public Specialty(Integer specialtyId, String name) {
        this.specialtyId = specialtyId;
        this.name = name;
    }


    public Integer getSpecialtyId() {
        return this.specialtyId;
    }

    public Specialty setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Specialty setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Vet> getVets() {
        return this.vets;
    }

    public Specialty setVets(Set<Vet> vets) {
        this.vets = vets;
        return this;
    }

}
