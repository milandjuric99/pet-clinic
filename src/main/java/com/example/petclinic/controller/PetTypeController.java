package com.example.petclinic.controller;

import java.util.Collection;

import com.example.petclinic.model.PetType;
import com.example.petclinic.service.impl.PetTypeServiceImpl;

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
@RequestMapping(value = "/api/pettypes")
public class PetTypeController {
    
    @Autowired
    private PetTypeServiceImpl petTypeService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<PetType>> getPetTypes(){
        
        Collection<PetType> petTypes = petTypeService.findAllPetTypes();
        if(petTypes == null){
            return new ResponseEntity<Collection<PetType>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Collection<PetType>>(petTypes, HttpStatus.OK);
    }

    @GetMapping(value = "/{petTypeId}", produces = "application/json")
    public ResponseEntity<PetType> getPetType(@PathVariable("petTypeId") Integer petTypeId){

        PetType petType = this.petTypeService.findPetTypeById(petTypeId);
        if(petType == null){
            return new ResponseEntity<PetType>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<PetType>(petType, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<PetType> addPetType(@RequestBody PetType petType, UriComponentsBuilder ucBuilder){
		HttpHeaders headers = new HttpHeaders();
		if(petType == null){
			return new ResponseEntity<PetType>(headers, HttpStatus.BAD_REQUEST);
		}
		this.petTypeService.savePetType(petType);
		headers.setLocation(ucBuilder.path("/api/pettypes/{id}").buildAndExpand(petType.getTypeId()).toUri());
		return new ResponseEntity<PetType>(petType, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{petTypeId}", produces = "application/json")
    public ResponseEntity<PetType> updatePetType(@PathVariable("petTypeId") Integer petTypeId, @RequestBody PetType petType,
    UriComponentsBuilder ucBuilder){
        
        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = petType.getTypeId() == null || petTypeId.equals(petType.getTypeId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<PetType>(headers, HttpStatus.BAD_REQUEST);
        }

        PetType currentPetType = this.petTypeService.findPetTypeById(petTypeId);
        if(currentPetType == null){
            return new ResponseEntity<PetType>(HttpStatus.NOT_FOUND);
        }
        currentPetType.setName(petType.getName());
        headers.setLocation(ucBuilder.path("api/pettypes/{id}").buildAndExpand(petType.getTypeId()).toUri());
        return new ResponseEntity<PetType>(currentPetType, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{petTypeId}", produces = "application/json")
    public ResponseEntity<Void> deletePetType(@PathVariable("petTypeId") Integer petTypeId){

        PetType petType = this.petTypeService.findPetTypeById(petTypeId);
        if(petType == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.petTypeService.deletePetType(petType);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
