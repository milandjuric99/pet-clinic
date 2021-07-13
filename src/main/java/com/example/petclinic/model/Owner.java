package com.example.petclinic.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer ownerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Collection<Pet> pets;

    public Owner() {
    }

    public Owner(Integer ownerId, String firstName, String lastName, String address, String city, String telephone,
            Collection<Pet> pets) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public Owner setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Owner setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Owner setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    public Owner setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return this.city;
    }

    public Owner setCity(String city) {
        this.city = city;
        return this;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Owner setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public Collection<Pet> getPets() {
        return this.pets;
    }

    public Owner setPets(Collection<Pet> pets) {
        this.pets = pets;
        return this;
    }

    public void addPet(Pet pet) {
        getPetsInternal().add(pet);
        pet.setOwner(this);
    }

    @JsonIgnore
    protected Set<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new HashSet<>();
        }
        return (Set<Pet>) this.pets;
    }

}
