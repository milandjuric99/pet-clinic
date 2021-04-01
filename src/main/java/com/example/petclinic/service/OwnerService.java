package com.example.petclinic.service;

import java.util.Collection;

import com.example.petclinic.model.Owner;

public interface OwnerService {
    
    Owner findOwnerById(Integer id);
	Collection<Owner> findAllOwners();
	void saveOwner(Owner owner);
	void deleteOwner(Owner owner) ;
	Collection<Owner> findOwnerByLastName(String lastName);

}
