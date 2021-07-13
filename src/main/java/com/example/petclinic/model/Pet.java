package com.example.petclinic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer petId;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<Visits> visits;


    public Pet() {
    }


    public Pet(Integer petId, String name, LocalDate birthDate, PetType type, Owner owner) {
        this.petId = petId;
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.owner = owner;
    }


    public Integer getPetId() {
        return petId;
    }

    public Pet setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Pet setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }


    public PetType getType() {
        return this.type;
    }

    public Pet setType(PetType type) {
        this.type = type;
        return this;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public Pet setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public Set<Visits> getVisits() {
        return this.visits;
    }

    public Pet setVisits(Set<Visits> visits) {
        this.visits = visits;
        return this;
    }

    @JsonIgnore
    protected Set<Visits> getVisitsInternal() {
        if (this.visits == null) {
            this.visits = new HashSet<>();
        }
        return this.visits;
    }

    public void addVisit(Visits visit) {
        getVisitsInternal().add(visit);
        visit.setPet(this);
    }

}
