package com.example.petclinic.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.PetController;
import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.model.PetType;
import com.example.petclinic.service.impl.PetServiceImpl;
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
public class PetControllerTest {
    
    @Autowired
    private PetController petController;

    @MockBean
    private PetServiceImpl petService;

    private MockMvc mockMvc;

    private List<Pet> pets;

    @BeforeEach
    public void initPets(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(petController)
        .build();

        pets = new ArrayList<Pet>();

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
    	pet.setPetId(3);
    	pet.setName("Rosy");
    	pet.setOwner(owner);
    	pet.setType(petType);
    	pets.add(pet);

    	pet = new Pet();
    	pet.setPetId(4);
    	pet.setName("Jewel");
    	pet.setOwner(owner);
    	pet.setType(petType);
    	pets.add(pet);
    }

    @Test
    void testGetPetSuccess() throws Exception{
        Mockito.when(this.petService.findPetById(3)).thenReturn(pets.get(0));

        this.mockMvc.perform(get("/api/pets/3")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.petId").value(3))
        .andExpect(jsonPath("$.name").value("Rosy"));
    }

    @Test
    void testGetPetNotFound() throws Exception {
    	Mockito.when(this.petService.findPetById(-1)).thenReturn(null);
        this.mockMvc.perform(get("/api/pets/-1")
        	.accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetPetsSuccess() throws Exception{
        Mockito.when(this.petService.findAllPets()).thenReturn(pets);

        this.mockMvc.perform(get("/api/pets/")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].petId").value(3))
        .andExpect(jsonPath("$[0].name").value("Rosy"))
        .andExpect(jsonPath("$[1].petId").value(4))
        .andExpect(jsonPath("$[1].name").value("Jewel"));
    }

    @Test
    void testGetPetsListNotFound() throws Exception{
        Mockito.when(this.petService.findAllPets()).thenReturn(null);

        this.mockMvc.perform(get("/api/pets/")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePetSuccess() throws Exception{
        Pet newPet = pets.get(0);
        newPet.setPetId(null);
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(newPet);

        this.mockMvc.perform(post("/api/pets/")
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated());
    }

    @Test
    void testCreatePetErrorSpecifiedId() throws Exception{
        Pet newPet = pets.get(0);
        newPet.setPetId(999);
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(newPet);

        this.mockMvc.perform(post("/api/pets/")
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePetError() throws Exception{
        Pet newPet = pets.get(0);
        newPet.setPetId(0);
        newPet.setName(null);
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(newPet);

        this.mockMvc.perform(post("/api/pets/")
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePetSuccess() throws Exception{
        Mockito.when(this.petService.findPetById(3)).thenReturn(pets.get(0));
        Pet updatedPet = new Pet();
        int petId = pets.get(0).getPetId();
        updatedPet.setPetId(petId);
        updatedPet.setName("Rosalie");
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(updatedPet);

        this.mockMvc.perform(put("/api/pets/" + petId)
    		.content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        	.andExpect(content().contentType("application/json"))
        	.andExpect(status().isNoContent());

    	this.mockMvc.perform(get("/api/pets/" + petId)
           	.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.petId").value(petId))
            .andExpect(jsonPath("$.name").value("Rosalie"));
    }

    @Test
    void testUpdatePetErrorBodyIdMismatchWithPathId() throws Exception {
        int petId = pets.get(0).getPetId();
        Pet updatedPet = new Pet();
        updatedPet.setPetId(-1);
        updatedPet.setName("Rosalie");
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(updatedPet);

        this.mockMvc.perform(put("/api/pets/" + petId)
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testDeletePetSuccess() throws Exception{
        Pet newPet = pets.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(newPet);
        Mockito.when(this.petService.findPetById(3)).thenReturn(pets.get(0));

        this.mockMvc.perform(delete("/api/pets/3")
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());

    }

    @Test
    void testDeletePetError() throws Exception{
        Pet newPet = pets.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String newPetAsJSON = mapper.writeValueAsString(newPet);
        Mockito.when(this.petService.findPetById(-1)).thenReturn(null);

        this.mockMvc.perform(delete("/api/pets/-1")
        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());

    }
}
