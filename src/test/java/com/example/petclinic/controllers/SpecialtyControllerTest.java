package com.example.petclinic.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.SpecialtyController;
import com.example.petclinic.model.Specialty;
import com.example.petclinic.service.impl.SpecialtyServiceImpl;
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
public class SpecialtyControllerTest {
    @Autowired
    private SpecialtyController specialtyController;

	@MockBean
    private SpecialtyServiceImpl specialtyService;

    private MockMvc mockMvc;

    private List<Specialty> specialties;

    @BeforeEach
    public void initSpecialties(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(specialtyController)
    			.build();
    	specialties = new ArrayList<Specialty>();

    	Specialty specialty = new Specialty();
    	specialty.setSpecialtyId(1);
    	specialty.setName("radiology");
    	specialties.add(specialty);

    	specialty = new Specialty();
    	specialty.setSpecialtyId(2);
    	specialty.setName("surgery");
    	specialties.add(specialty);

    	specialty = new Specialty();
    	specialty.setSpecialtyId(3);
    	specialty.setName("dentistry");
    	specialties.add(specialty);

    }

    @Test
    void testGetSpecialtySuccess() throws Exception {
    	Mockito.when(this.specialtyService.findById(1)).thenReturn(specialties.get(0));

        this.mockMvc.perform(get("/api/specialties/1")
        	.accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.specialtyId").value(1))
            .andExpect(jsonPath("$.name").value("radiology"));
    }

    @Test
    void testGetSpecialtyNotFound() throws Exception {
    	Mockito.when(this.specialtyService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(get("/api/specialties/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllSpecialtysSuccess() throws Exception {
    	specialties.remove(0);
    	Mockito.when(this.specialtyService.findAll()).thenReturn(specialties);

        this.mockMvc.perform(get("/api/specialties/")
        	.accept(MediaType.APPLICATION_JSON))
  .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
        	.andExpect(jsonPath("$.[0].specialtyId").value(2))
        	.andExpect(jsonPath("$.[0].name").value("surgery"))
        	.andExpect(jsonPath("$.[1].specialtyId").value(3))
        	.andExpect(jsonPath("$.[1].name").value("dentistry"));
    }

    @Test
    void testGetAllSpecialtysNotFound() throws Exception {
    	specialties.clear();
    	Mockito.when(this.specialtyService.findAll()).thenReturn(null);

        this.mockMvc.perform(get("/api/specialties/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSpecialtySuccess() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	newSpecialty.setSpecialtyId(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

    	this.mockMvc.perform(post("/api/specialties/")
    		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());
    }

    @Test
    void testCreateSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	newSpecialty.setSpecialtyId(55);
    	newSpecialty.setName(null);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

    	this.mockMvc.perform(post("/api/specialties/")
        		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isBadRequest());
     }

    @Test
    void testUpdateSpecialtySuccess() throws Exception {
    	Mockito.when(this.specialtyService.findById(2)).thenReturn(specialties.get(1));
    	Specialty newSpecialty = specialties.get(1);
    	newSpecialty.setName("surgery I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

    	this.mockMvc.perform(put("/api/specialties/2")
    		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/specialties/2")
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.specialtyId").value(2))
            .andExpect(jsonPath("$.name").value("surgery I"));
    }

    @Test
    void testUpdateSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
		newSpecialty.setSpecialtyId(0);
    	newSpecialty.setName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);

    	this.mockMvc.perform(put("/api/specialties/1")
    		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
     }

    @Test
    void testDeleteSpecialtySuccess() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);
    	Mockito.when(this.specialtyService.findById(1)).thenReturn(specialties.get(0));

    	this.mockMvc.perform(delete("/api/specialties/1")
    		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSpecialtyError() throws Exception {
    	Specialty newSpecialty = specialties.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newSpecialtyAsJSON = mapper.writeValueAsString(newSpecialty);
    	Mockito.when(this.specialtyService.findById(-1)).thenReturn(null);

    	this.mockMvc.perform(delete("/api/specialties/-1")
    		.content(newSpecialtyAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
    }
}
