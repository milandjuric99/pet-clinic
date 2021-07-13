package com.example.petclinic.controller;

import org.springframework.http.HttpHeaders;
import java.util.Collection;

import com.example.petclinic.model.Owner;
import com.example.petclinic.service.impl.OwnerServiceImpl;

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
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerServiceImpl ownerService;

    @GetMapping(value = "/lastName/{lastName}", produces = "application/json")
    public ResponseEntity<Collection<Owner>> getOwnersList(@PathVariable("lastName") String ownerLastName) {
        Collection<Owner> owners = this.ownerService.findOwnerByLastName(ownerLastName);
        if (owners.isEmpty()) {
            return new ResponseEntity<Collection<Owner>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Owner>>(owners, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Owner>> getOwners() {
        Collection<Owner> owners = this.ownerService.findAllOwners();
        if (owners.isEmpty()) {
            return new ResponseEntity<Collection<Owner>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Owner>>(owners, HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerId}", produces = "application/json")
    public ResponseEntity<Owner> getOwner(@PathVariable("ownerId") int ownerId) {
        Owner owner = null;
        owner = this.ownerService.findOwnerById(ownerId);
        if (owner == null) {
            return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Owner>(owner, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner, BindingResult bindingResult,
            UriComponentsBuilder ucBuilder) {
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || owner.getOwnerId() != null) {
            return new ResponseEntity<Owner>(headers, HttpStatus.BAD_REQUEST);
        }
        this.ownerService.saveOwner(owner);
        headers.setLocation(ucBuilder.path("/api/owners/{id}").buildAndExpand(owner.getOwnerId()).toUri());
        return new ResponseEntity<Owner>(owner, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{ownerId}", produces = "application/json")
    public ResponseEntity<Owner> updateOwner(@PathVariable("ownerId") Integer ownerId, @RequestBody Owner owner,
    BindingResult bindingResult, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        boolean bodyIdMatchesPathId = owner.getOwnerId() == null || ownerId.equals(owner.getOwnerId());
        if(bindingResult.hasErrors() || !bodyIdMatchesPathId){
			return new ResponseEntity<Owner>(headers, HttpStatus.BAD_REQUEST);
        }

        Owner currentOwner = this.ownerService.findOwnerById(ownerId);
        if(currentOwner == null){
            return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
        }
        currentOwner.setAddress(owner.getAddress());
        currentOwner.setCity(owner.getCity());
        currentOwner.setFirstName(owner.getFirstName());
        currentOwner.setLastName(owner.getLastName());
        currentOwner.setTelephone(owner.getTelephone());
        this.ownerService.saveOwner(currentOwner);
        headers.setLocation(ucBuilder.path("/api/owners/{id}").buildAndExpand(owner.getOwnerId()).toUri());
        return new ResponseEntity<Owner>(currentOwner, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{ownerId}", produces = "application/json")
    public ResponseEntity<Void> deleteOwner(@PathVariable("ownerId") Integer ownerId) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		if (owner == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.ownerService.deleteOwner(owner);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
