package com.example.petclinic.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.VisitsController;
import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.model.PetType;
import com.example.petclinic.model.Visits;
import com.example.petclinic.service.impl.VisitsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
public class VisitsControllerTest {
    
    @Autowired
    private VisitsController visitsController;

    @MockBean
    private VisitsServiceImpl visitService;

    private List<Visits> visits;

    private MockMvc mockMvc;

    @BeforeEach
    public void initVisits(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(visitsController)
    			.build();

    	visits = new ArrayList<Visits>();

    	Owner owner = new Owner();
    	owner.setOwnerId(1);
    	owner.setFirstName("Eduardo");
    	owner.setLastName("Rodriquez");
    	owner.setAddress("2693 Commerce St.");
    	owner.setCity("McFarland");
    	owner.setTelephone("6085558763");

    	PetType petType = new PetType();
    	petType.setTypeId(2);
    	petType.setName("dog");

    	Pet pet = new Pet();
    	pet.setPetId(8);
    	pet.setName("Rosy");
    	pet.setOwner(owner);
    	pet.setType(petType);


    	Visits visit = new Visits();
    	visit.setVisitId(2);
    	visit.setPet(pet);
    	visit.setDescription("rabies shot");
    	visits.add(visit);

    	visit = new Visits();
    	visit.setVisitId(3);
    	visit.setPet(pet);
    	visit.setDescription("neutered");
    	visits.add(visit);
    }

    @Test
    void testGetVisitSuccess() throws Exception {
    	Mockito.when(this.visitService.findByVisitId(2)).thenReturn(visits.get(0));

        this.mockMvc.perform(get("/api/visits/2")
        	.accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.visitId").value(2))
            .andExpect(jsonPath("$.description").value("rabies shot"));
    }

    @Test
    void testGetVisitNotFound() throws Exception {
    	Mockito.when(this.visitService.findByVisitId(-1)).thenReturn(null);

        this.mockMvc.perform(get("/api/visits/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllVisitsSuccess() throws Exception {
    	Mockito.when(this.visitService.findAllVisits()).thenReturn(visits);

        this.mockMvc.perform(get("/api/visits/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
        	.andExpect(jsonPath("$.[0].visitId").value(2))
        	.andExpect(jsonPath("$.[0].description").value("rabies shot"))
        	.andExpect(jsonPath("$.[1].visitId").value(3))
        	.andExpect(jsonPath("$.[1].description").value("neutered"));
    }

    @Test
    void testGetAllVisitsNotFound() throws Exception {
    	visits.clear();
    	Mockito.when(this.visitService.findAllVisits()).thenReturn(null);

        this.mockMvc.perform(get("/api/visits/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVisitSuccess() throws Exception {
    	Visits newVisit = visits.get(0);
    	newVisit.setVisitId(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

    	this.mockMvc.perform(post("/api/visits/")
    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());
    }

    @Test
    void testCreateVisitError() throws Exception {
    	Visits newVisit = visits.get(0);
    	newVisit.setVisitId(434);
    	newVisit.setPet(null);
        newVisit.setDescription(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

    	this.mockMvc.perform(post("/api/visits/")
        		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isBadRequest());
     }

    @Test
    void testUpdateVisitSuccess() throws Exception {
    	Mockito.when(this.visitService.findByVisitId(2)).thenReturn(visits.get(0));
    	Visits newVisit = visits.get(0);
    	newVisit.setDescription("rabies shot test");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

    	this.mockMvc.perform(put("/api/visits/2")
    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/visits/2")
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.visitId").value(2))
            .andExpect(jsonPath("$.description").value("rabies shot test"));
    }

    @Test
    void testUpdateVisitError() throws Exception {
    	Visits newVisit = visits.get(0);
    	newVisit.setPet(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);

    	this.mockMvc.perform(put("/api/visits/2")
    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
     }

    @Test
    void testDeleteVisitSuccess() throws Exception {
    	Visits newVisit = visits.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);
    	Mockito.when(this.visitService.findByVisitId(2)).thenReturn(visits.get(0));

    	this.mockMvc.perform(delete("/api/visits/2")
    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNoContent());
    }

    @Test
    void testDeleteVisitError() throws Exception {
    	Visits newVisit = visits.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVisitAsJSON = mapper.writeValueAsString(newVisit);
    	Mockito.when(this.visitService.findByVisitId(-1)).thenReturn(null);
		
    	this.mockMvc.perform(delete("/api/visits/-1")
    		.content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
    }

}
