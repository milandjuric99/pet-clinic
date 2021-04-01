package com.example.petclinic.controller;

import org.springframework.http.HttpHeaders;
import java.util.Collection;

import com.example.petclinic.model.Pet;
import com.example.petclinic.service.PetServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/api/pets")
public class PetController {
    
    @Autowired
    private PetServiceImpl petService;

    @GetMapping(value = "/{petId}", produces = "application/json")
    public ResponseEntity<Pet> getPet(@PathVariable("petId") Integer petId){
        Pet pet = petService.findPetById(petId);
        if(pet == null){
            return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pet>(pet, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Pet>> getPets(){
        Collection<Pet> pets = petService.findAllPets();
        if(pets == null){
            return new ResponseEntity<Collection<Pet>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Pet>>(pets, HttpStatus.OK);
    } 

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet, BindingResult bindingResult,
    UriComponentsBuilder ucBuilder){
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || pet.getPetId() != null){
            return new ResponseEntity<Pet>(headers, HttpStatus.BAD_REQUEST);
        }
        this.petService.savePet(pet);
        headers.setLocation(ucBuilder.path("/api/pets/{id}").buildAndExpand(pet.getPetId()).toUri());
        return new ResponseEntity<Pet>(pet, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{petId}", produces = "application/json")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet, @PathVariable("petId") Integer petId, BindingResult bindingResult,
    UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = pet.getPetId() == null || petId.equals(pet.getPetId());
        if(bindingResult.hasErrors() || !bodyIdMatchesPathId){
            return new ResponseEntity<Pet>(headers, HttpStatus.BAD_REQUEST);
        }

        Pet currentPet = this.petService.findPetById(petId);
        if(currentPet == null){
            return new ResponseEntity<Pet>(HttpStatus.NOT_FOUND);
        }
        currentPet.setName(pet.getName());
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setOwner(pet.getOwner());
        currentPet.setType(pet.getType());
        this.petService.savePet(currentPet);
        headers.setLocation(ucBuilder.path("/api/pets/{id}").buildAndExpand(pet.getPetId()).toUri());
        return new ResponseEntity<Pet>(currentPet, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{petId}", produces = "application/json")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") Integer petId){

        Pet pet = this.petService.findPetById(petId);
        if(pet == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.petService.deletePet(pet);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
