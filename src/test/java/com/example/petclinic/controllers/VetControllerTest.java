package com.example.petclinic.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.VetController;
import com.example.petclinic.model.Vet;
import com.example.petclinic.service.impl.VetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
public class VetControllerTest {
    
    @Autowired
    private VetController vetController;

    @MockBean
    private VetServiceImpl vetService;

    private MockMvc mockMvc;

    private List<Vet> vets;

    @BeforeEach
    public void initVets(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(vetController)
    			.build();
    	vets = new ArrayList<Vet>();


    	Vet vet = new Vet();
    	vet.setVetId(1);
    	vet.setFirstName("James");
    	vet.setLastName("Carter");
    	vets.add(vet);

    	vet = new Vet();
    	vet.setVetId(2);
    	vet.setFirstName("Helen");
    	vet.setLastName("Leary");
    	vets.add(vet);

    	vet = new Vet();
    	vet.setVetId(3);
    	vet.setFirstName("Linda");
    	vet.setLastName("Douglas");
    	vets.add(vet);
    }

    @Test
    void testGetVetSuccess() throws Exception {
    	Mockito.when(this.vetService.findById(1)).thenReturn(vets.get(0));

        this.mockMvc.perform(get("/api/vets/1")
        	.accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.vetId").value(1))
            .andExpect(jsonPath("$.firstName").value("James"));
    }

    @Test
    void testGetVetNotFound() throws Exception {
    	Mockito.when(this.vetService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(get("/api/vets/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllVetsSuccess() throws Exception {
    	Mockito.when(this.vetService.findAll()).thenReturn(vets);

        this.mockMvc.perform(get("/api/vets/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].vetId").value(1))
            .andExpect(jsonPath("$.[0].firstName").value("James"))
            .andExpect(jsonPath("$.[1].vetId").value(2))
            .andExpect(jsonPath("$.[1].firstName").value("Helen"));
    }

    @Test
    void testGetAllVetsNotFound() throws Exception {
    	vets.clear();
    	Mockito.when(this.vetService.findAll()).thenReturn(null);

        this.mockMvc.perform(get("/api/vets/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVetSuccess() throws Exception {
    	Vet newVet = vets.get(0);
    	newVet.setVetId(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

    	this.mockMvc.perform(post("/api/vets/")
    		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());
    }

    @Test
    void testCreateVetError() throws Exception {
    	Vet newVet = vets.get(0);
    	newVet.setVetId(99);
    	newVet.setFirstName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

    	this.mockMvc.perform(post("/api/vets/")
        		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isBadRequest());
     }

    @Test
    void testUpdateVetSuccess() throws Exception {
    	Mockito.when(this.vetService.findById(1)).thenReturn(vets.get(0));

    	Vet newVet = vets.get(0);
    	newVet.setFirstName("James");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

    	this.mockMvc.perform(put("/api/vets/1")
    		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/vets/1")
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.vetId").value(1))
            .andExpect(jsonPath("$.firstName").value("James"));

    }

    @Test
    void testUpdateVetError() throws Exception {
    	Vet newVet = vets.get(0);
        newVet.setVetId(0);
    	newVet.setFirstName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);

    	this.mockMvc.perform(put("/api/vets/1")
    		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
     }

    @Test
    void testDeleteVetSuccess() throws Exception {
    	Vet newVet = vets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);
    	Mockito.when(this.vetService.findById(1)).thenReturn(vets.get(0));

    	this.mockMvc.perform(delete("/api/vets/1")
    		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNoContent());
    }

    @Test
    void testDeleteVetError() throws Exception {
    	Vet newVet = vets.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newVetAsJSON = mapper.writeValueAsString(newVet);
    	Mockito.when(this.vetService.findById(-1)).thenReturn(null);
		
    	this.mockMvc.perform(delete("/api/vets/-1")
    		.content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
    }

}
