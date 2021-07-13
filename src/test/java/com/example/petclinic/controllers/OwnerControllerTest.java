package com.example.petclinic.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.OwnerController;
import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.model.PetType;
import com.example.petclinic.model.Visits;
import com.example.petclinic.service.impl.OwnerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private OwnerController ownerRestController;

    @MockBean
    private OwnerServiceImpl ownerService;

    private MockMvc mockMvc;
    
    private List<Owner> owners;

    @BeforeEach
    public void initOwners(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(ownerRestController)
    			.build();
    	owners = new ArrayList<Owner>();

    	Owner ownerWithPet = new Owner();
    	ownerWithPet.setOwnerId(1);
    	ownerWithPet.setFirstName("George");
    	ownerWithPet.setLastName("Franklin");
    	ownerWithPet.setAddress("110 W. Liberty St.");
    	ownerWithPet.setCity("Madison");
    	ownerWithPet.setTelephone("6085551023");
    	ownerWithPet.addPet(getTestPetWithIdAndName(ownerWithPet, 1, "Rosy"));
    	owners.add(ownerWithPet);

        Owner owner = new Owner();
    	owner.setOwnerId(2);
    	owner.setFirstName("Betty");
    	owner.setLastName("Davis");
    	owner.setAddress("638 Cardinal Ave.");
    	owner.setCity("Sun Prairie");
    	owner.setTelephone("6085551749");
    	owners.add(owner);

    	owner = new Owner();
    	owner.setOwnerId(3);
    	owner.setFirstName("Eduardo");
    	owner.setLastName("Rodriquez");
    	owner.setAddress("2693 Commerce St.");
    	owner.setCity("McFarland");
    	owner.setTelephone("6085558763");
    	owners.add(owner);

    	owner = new Owner();
    	owner.setOwnerId(4);
    	owner.setFirstName("Harold");
    	owner.setLastName("Davis");
    	owner.setAddress("563 Friendly St.");
    	owner.setCity("Windsor");
    	owner.setTelephone("6085553198");
    	owners.add(owner);
    }
    private Pet getTestPetWithIdAndName(final Owner owner, final int id, final String name) {
        PetType petType = new PetType();
        petType.setTypeId(2);
        petType.setName("dog");
        Pet pet = new Pet();
        pet.setPetId(id);
        pet.setName(name);
        pet.setOwner(owner);
        pet.setType(petType);
        pet.addVisit(getTestVisitForPet(pet, 1));
        return pet;
    }

    private Visits getTestVisitForPet(final Pet pet, final int id) {
        Visits visit = new Visits();
        visit.setVisitId(id);
        visit.setPet(pet);
        visit.setDate(new Date());
        visit.setDescription("test" + id);
        return visit;
    }
    @Test
    void testGetOwnerSuccess() throws Exception {
    	Mockito.when(this.ownerService.findOwnerById(1)).thenReturn(owners.get(0));

        this.mockMvc.perform(get("/api/owners/1")
        	.accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.ownerId").value(1))
            .andExpect(jsonPath("$.firstName").value("George"));
    }

    @Test
        void testGetOwnerNotFound() throws Exception {
    	Mockito.when(this.ownerService.findOwnerById(-1)).thenReturn(null);

        this.mockMvc.perform(get("/api/owners/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetOwnersSuccess() throws Exception{
        owners.remove(0);
    	owners.remove(1);
        Mockito.when(this.ownerService.findOwnerByLastName("Davis")).thenReturn(owners);

        this.mockMvc.perform(get("/api/owners/lastName/Davis")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].ownerId").value(2))
            .andExpect(jsonPath("$.[0].firstName").value("Betty"))
            .andExpect(jsonPath("$.[1].ownerId").value(4))
            .andExpect(jsonPath("$.[1].firstName").value("Harold"));
    }

    @Test
    void testGetOwnersListNotFound() throws Exception {
    	owners.clear();
    	Mockito.when(this.ownerService.findOwnerByLastName("0")).thenReturn(owners);

        this.mockMvc.perform(get("/api/owners/?lastName=0")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void getAllOwnersSucces() throws Exception{
        owners.remove(0);
        owners.remove(1);
        Mockito.when(this.ownerService.findAllOwners()).thenReturn(owners);

        this.mockMvc.perform(get("/api/owners/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].ownerId").value(2))
        .andExpect(jsonPath("$[0].firstName").value("Betty"))
        .andExpect(jsonPath("$[1].ownerId").value(4))
        .andExpect(jsonPath("$[1].firstName").value("Harold"));
    } 
    
    @Test
    void testGetAllOwnersNotFound() throws Exception{
        owners.clear();
        Mockito.when(this.ownerService.findAllOwners()).thenReturn(owners);

        this.mockMvc.perform(get("/api/owners/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOwnerSuccess() throws Exception{
        Owner newOwner = owners.get(0);
    	newOwner.setOwnerId(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newOwnerAsJSON = mapper.writeValueAsString(newOwner);
        
    	this.mockMvc.perform(post("/api/owners/")
    		.content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());  
    }    

    @Test
    void testCreateOwnerErrorSpecifiedId() throws Exception{
        Owner newOwner = owners.get(0);
        newOwner.setOwnerId(999);
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(newOwner);

        this.mockMvc.perform(post("/api/owners/")
            .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOwnerError() throws Exception{
        Owner newOwner = owners.get(0);
        newOwner.setOwnerId(0);
        newOwner.setFirstName(null);
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(newOwner);

        this.mockMvc.perform(post("/api/owners/")
                .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOwnerSuccess() throws Exception{
        Mockito.when(this.ownerService.findOwnerById(1)).thenReturn(owners.get(0));
        int ownerId = owners.get(0).getOwnerId();
        Owner updatedOwner = new Owner();
        updatedOwner.setOwnerId(ownerId);
        updatedOwner.setFirstName("George I");
        updatedOwner.setLastName("Franklin");
        updatedOwner.setAddress("110 W. Liberty St.");
        updatedOwner.setCity("Madison");
        updatedOwner.setTelephone("6085551023");
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(updatedOwner);

        this.mockMvc.perform(put("/api/owners/" + ownerId)
        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().contentType("application/json"))
        .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/owners/" + ownerId)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.ownerId").value(ownerId))
            .andExpect(jsonPath("$.firstName").value("George I"));

    }

    @Test
    void testUpdateOwnerSuccessNoBodyId() throws Exception{
        Mockito.when(this.ownerService.findOwnerById(1)).thenReturn(owners.get(0));
        int ownerId = owners.get(0).getOwnerId();
        Owner updatedOwner = new Owner();
        updatedOwner.setOwnerId(ownerId);
        updatedOwner.setFirstName("George I");
        updatedOwner.setLastName("Franklin");
        updatedOwner.setAddress("110 W. Liberty St.");
        updatedOwner.setCity("Madison");
        updatedOwner.setTelephone("6085551023");
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(updatedOwner);

        this.mockMvc.perform(put("/api/owners/" + ownerId)
        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().contentType("application/json"))
        .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/owners/" + ownerId)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.ownerId").value(ownerId))
        .andExpect(jsonPath("$.firstName").value("George I"));
    }

    @Test
    void testUpdateOwnerErrorBodyIdMismatchWithPathId() throws Exception {
        int ownerId = owners.get(0).getOwnerId();
        Owner updatedOwner = new Owner();
        updatedOwner.setOwnerId(-1);
        updatedOwner.setFirstName("George I");
        updatedOwner.setLastName("Franklin");
        updatedOwner.setAddress("110 W. Liberty St.");
        updatedOwner.setCity("Madison");
        updatedOwner.setTelephone("6085551023");
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(updatedOwner);

        this.mockMvc.perform(put("/api/owners/" + ownerId)
            .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteOwnerSuccess() throws Exception{
        Owner newOwner = owners.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(newOwner);
        Mockito.when(this.ownerService.findOwnerById(1)).thenReturn(owners.get(0));

        this.mockMvc.perform(delete("/api/owners/1")
        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOwnerError() throws Exception{
        Owner newOwner = owners.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String newOwnerAsJSON = mapper.writeValueAsString(newOwner);
        Mockito.when(this.ownerService.findOwnerById(-1)).thenReturn(null);

        this.mockMvc.perform(delete("/api/owners/-1")
        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
    }
}

