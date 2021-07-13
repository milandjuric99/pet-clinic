package com.example.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "visits")
public class Visits {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer visitId;

    @Column(name = "visit_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    private Date date;

    @Column(name = "description")
    private String description;

    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;


    public Visits() {
        this.date = new Date();
    }

    public Visits(Integer visitId, Date date, String description, Pet pet) {
        this.visitId = visitId;
        this.date = date;
        this.description = description;
        this.pet = pet;
    }


    public Integer getVisitId() {
        return this.visitId;
    }

    public Visits setVisitId(Integer visitId) {
        this.visitId = visitId;
        return this;
    }

    public Date getDate() {
        return this.date;
    }

    public Visits setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Visits setDescription(String description) {
        this.description = description;
        return this;
    }

    public Pet getPet() {
        return this.pet;
    }

    public Visits setPet(Pet pet) {
        this.pet = pet;
        return this;
    }

}
