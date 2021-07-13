package com.example.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "vets")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer vetId;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "vet_specialties",
        joinColumns = @JoinColumn(name = "vet_id"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Specialty> specialties;


    public Vet() {
    }

    public Vet(Integer vetId, String firstName, String lastName, Set<Specialty> specialties) {
        this.vetId = vetId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialties = specialties;
    }
    

    public Integer getVetId() {
        return this.vetId;
    }

    public Vet setVetId(Integer vetId) {
        this.vetId = vetId;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Vet setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Vet setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<Specialty> getSpecialties() {
        return this.specialties;
    }

    public Vet setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
        return this;
    }

}
