package com.example.petclinic.controller;

import java.util.Collection;

import com.example.petclinic.model.Visits;
import com.example.petclinic.service.impl.VisitsServiceImpl;

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
@RequestMapping(value = "/api/visits")
public class VisitsController {
    
    @Autowired
    private VisitsServiceImpl visitsService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Visits>> getVisits(){

        Collection<Visits> visits = this.visitsService.findAllVisits();
        if(visits == null){
            return new ResponseEntity<Collection<Visits>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Visits>>(visits, HttpStatus.OK);
    }

    @GetMapping(value = "/pet/{petId}", produces = "application/json")
    public ResponseEntity<Collection<Visits>> getVisitsByPetId(@PathVariable("petId") Integer petId){

        Collection<Visits> visits = this.visitsService.findAllVisitsByPetId(petId);
        if(visits == null){
            return new ResponseEntity<Collection<Visits>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Visits>>(visits, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Visits> getVisit(@PathVariable("id") Integer id){

        Visits visit = this.visitsService.findByVisitId(id);
        if(visit == null){
            return new ResponseEntity<Visits>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Visits>(visit, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Visits> addVisit(@RequestBody Visits visit, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        if(visit.getVisitId() != null){
            return new ResponseEntity<Visits>(headers, HttpStatus.BAD_REQUEST);
        }
        this.visitsService.save(visit);
        headers.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getVisitId()).toUri());
        return new ResponseEntity<Visits>(visit, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Visits> updateVisit(@PathVariable("id") Integer id, @RequestBody Visits visit,
    UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = visit.getVisitId() == null || id.equals(visit.getVisitId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<Visits>(headers, HttpStatus.BAD_REQUEST);
        }

        Visits currentVisit = this.visitsService.findByVisitId(visit.getVisitId());
        if(currentVisit == null){
            return new ResponseEntity<Visits>(headers, HttpStatus.NOT_FOUND);
        }
        currentVisit.setDate(visit.getDate());
        currentVisit.setDescription(visit.getDescription());
        currentVisit.setPet(visit.getPet());
        this.visitsService.save(currentVisit);
        headers.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getVisitId()).toUri());
        return new ResponseEntity<Visits>(visit, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value ="/{id}", produces = "application/json")
    public ResponseEntity<Void> delteVisit(@PathVariable("id") Integer id){

        Visits visit = this.visitsService.findByVisitId(id);
        if(visit == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.visitsService.delete(visit);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
