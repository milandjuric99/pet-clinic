package com.example.petclinic.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.PetTypeController;
import com.example.petclinic.model.PetType;
import com.example.petclinic.service.impl.PetTypeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
public class PetTypeContollerTest {
    
    @Autowired
    private PetTypeController petTypeRestController;

    @MockBean
    private PetTypeServiceImpl petTypeService;

    private MockMvc mockMvc;

    private List<PetType> petTypes;

    @BeforeEach
    public void initPetTypes(){
    	this.mockMvc = MockMvcBuilders.standaloneSetup(petTypeRestController)
    			.build();
    	petTypes = new ArrayList<PetType>();

    	PetType petType = new PetType();
    	petType.setTypeId(1);
    	petType.setName("cat");
    	petTypes.add(petType);

    	petType = new PetType();
    	petType.setTypeId(2);
    	petType.setName("dog");
    	petTypes.add(petType);

    	petType = new PetType();
    	petType.setTypeId(3);
    	petType.setName("lizard");
    	petTypes.add(petType);

    	petType = new PetType();
    	petType.setTypeId(4);
    	petType.setName("snake");
    	petTypes.add(petType);
    }

    @Test
	void testGetPetTypeSuccess() throws Exception {
        Mockito.when(this.petTypeService.findPetTypeById(1)).thenReturn(petTypes.get(0));

		this.mockMvc.perform(get("/api/pettypes/1")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.typeId").value(1))
            .andExpect(jsonPath("$.name").value("cat"));
    }

    @Test
	void testGetPetTypeNotFound() throws Exception {
    	Mockito.when(this.petTypeService.findPetTypeById(-1)).thenReturn(null);
        this.mockMvc.perform(get("/api/pettypes/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
	void testGetAllPetTypesSuccess() throws Exception {
        petTypes.remove(0);
        petTypes.remove(1);
        Mockito.when(this.petTypeService.findAllPetTypes()).thenReturn(petTypes);
        this.mockMvc.perform(get("/api/pettypes/")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.[0].typeId").value(2))
            .andExpect(jsonPath("$.[0].name").value("dog"))
            .andExpect(jsonPath("$.[1].typeId").value(4))
            .andExpect(jsonPath("$.[1].name").value("snake"));
    }

    @Test
	void testGetAllPetTypesNotFound() throws Exception {
    	Mockito.when(this.petTypeService.findAllPetTypes()).thenReturn(null);
        this.mockMvc.perform(get("/api/pettypes/")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
	void testCreatePetTypeSuccess() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	newPetType.setTypeId(999);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
    	this.mockMvc.perform(post("/api/pettypes/")
    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
    		.andExpect(status().isCreated());
    }

    @Test
	void testCreatePetTypeError() throws Exception {
    	PetType newPetType = null;
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
    	this.mockMvc.perform(post("/api/pettypes/")
        		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isBadRequest())
				.andDo(print());
     }

    @Test
	void testUpdatePetTypeSuccess() throws Exception {
    	Mockito.when(this.petTypeService.findPetTypeById(2)).thenReturn(petTypes.get(1));
    	PetType newPetType = petTypes.get(1);
    	newPetType.setName("dog I");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
    	this.mockMvc.perform(put("/api/pettypes/2")
    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/pettypes/2")
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.typeId").value(2))
            .andExpect(jsonPath("$.name").value("dog I"));
    }

    @Test
	void testUpdatePetTypeError() throws Exception {
    	PetType newPetType = petTypes.get(0);
		newPetType.setTypeId(-1);
    	newPetType.setName("");
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);

    	this.mockMvc.perform(put("/api/pettypes/1")
    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isBadRequest());
     }

    @Test
	void testDeletePetTypeSuccess() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
    	Mockito.when(this.petTypeService.findPetTypeById(1)).thenReturn(petTypes.get(0));
    	this.mockMvc.perform(delete("/api/pettypes/1")
    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNoContent());
    }

    @Test
	void testDeletePetTypeError() throws Exception {
    	PetType newPetType = petTypes.get(0);
    	ObjectMapper mapper = new ObjectMapper();
    	String newPetTypeAsJSON = mapper.writeValueAsString(newPetType);
    	Mockito.when(this.petTypeService.findPetTypeById(-1)).thenReturn(null);
    	this.mockMvc.perform(delete("/api/pettypes/-1")
    		.content(newPetTypeAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(status().isNotFound());
    }

}
