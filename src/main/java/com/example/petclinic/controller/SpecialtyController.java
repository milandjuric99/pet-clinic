package com.example.petclinic.controller;

import java.util.Collection;

import com.example.petclinic.model.Specialty;
import com.example.petclinic.service.impl.SpecialtyServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/specialties")
public class SpecialtyController {
    
    @Autowired
    private SpecialtyServiceImpl specialtyService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Specialty>> getSpecialties(){

        Collection<Specialty> specialties = this.specialtyService.findAll();
        if(specialties == null){
            return new ResponseEntity<Collection<Specialty>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Specialty>>(specialties, HttpStatus.OK);
    }

    @GetMapping(value = "/{specialtyId}", produces = "application/json")
    public ResponseEntity<Specialty> getSpecialty(@PathVariable("specialtyId") Integer specialtyId){

        Specialty specialty = this.specialtyService.findById(specialtyId);
        if(specialty == null){
            return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Specialty>(specialty, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Specialty> addSpecialty(@RequestBody Specialty specialty, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        if(specialty.getSpecialtyId() != null){
            return new ResponseEntity<Specialty>(headers, HttpStatus.BAD_REQUEST);
        }
        this.specialtyService.save(specialty);
        headers.setLocation(ucBuilder.path("/api/specialty/{specialtyId}").buildAndExpand(specialty.getSpecialtyId()).toUri());
        return new ResponseEntity<Specialty>(specialty, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{specialtyId}", produces = "application/json")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable("specialtyId") Integer specialtyId, @RequestBody Specialty specialty,
    UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = this.specialtyService.findById(specialtyId) == null || 
        specialtyId.equals(specialty.getSpecialtyId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<Specialty>(headers, HttpStatus.BAD_REQUEST);
        }
        Specialty currentSpecialty = this.specialtyService.findById(specialtyId);
        if(currentSpecialty == null){
            return new ResponseEntity<Specialty>(HttpStatus.NOT_FOUND);
        }
        currentSpecialty.setName(specialty.getName());
        currentSpecialty.setVets(specialty.getVets());
        this.specialtyService.save(currentSpecialty);
        headers.setLocation(ucBuilder.path("/api/specialty/{specialtyId}").buildAndExpand(specialty.getSpecialtyId()).toUri());
        return new ResponseEntity<Specialty>(currentSpecialty, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{specialtyId}", produces = "application/json")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable("specialtyId") Integer specialtyId){

        Specialty specialty = this.specialtyService.findById(specialtyId);
        if(specialty == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.specialtyService.delete(specialty);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
