package com.example.petclinic.controller;

import java.util.Collection;

import com.example.petclinic.model.Vet;
import com.example.petclinic.service.impl.VetServiceImpl;

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
@RequestMapping("/api/vets")
public class VetController {
    
    @Autowired
    private VetServiceImpl vetService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Vet>> getVets(){
        
        Collection<Vet> vets = this.vetService.findAll();
        if(vets == null){
            return new ResponseEntity<Collection<Vet>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Vet>>(vets, HttpStatus.OK);
    }

    @GetMapping(value = "/{vetId}", produces = "application/json")
    public ResponseEntity<Vet> findVet(@PathVariable("vetId") Integer vetId){

        Vet vet = this.vetService.findById(vetId);
        if(vet == null){
            return new ResponseEntity<Vet>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Vet>(vet, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Vet> addVet(@RequestBody Vet vet, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        if(vet.getVetId() != null){
            return new ResponseEntity<Vet>(headers, HttpStatus.BAD_REQUEST);
        }
        this.vetService.save(vet);
        headers.setLocation(ucBuilder.path("/api/vets/{vetId}").buildAndExpand(vet.getVetId()).toUri());
        return new ResponseEntity<Vet>(vet, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{vetId}", produces = "application/json")
    public ResponseEntity<Vet> updateVet(@PathVariable("vetId") Integer vetId, @RequestBody Vet vet,
    UriComponentsBuilder ucBuilder){
        
        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = this.vetService.findById(vetId) == null ||
        vetId.equals(vet.getVetId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<Vet>(HttpStatus.BAD_REQUEST);
        }
        Vet currentVet = this.vetService.findById(vetId);
        if(currentVet == null){
            return new ResponseEntity<Vet>(HttpStatus.NOT_FOUND);
        }
        currentVet.setFirstName(vet.getFirstName());
        currentVet.setLastName(vet.getFirstName());
        currentVet.setSpecialties(vet.getSpecialties());
        this.vetService.save(currentVet);
        headers.setLocation(ucBuilder.path("/api/vets/{vetId}").buildAndExpand(vet.getVetId()).toUri());
        return new ResponseEntity<Vet>(currentVet, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{vetId}", produces = "application/json")
    public ResponseEntity<Void> deleteVet(@PathVariable("vetId") Integer vetId){

        Vet vet = this.vetService.findById(vetId);
        if(vet == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.vetService.delete(vet);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}

