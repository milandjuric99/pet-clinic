package com.example.petclinic.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.UserController;
import com.example.petclinic.model.Role;
import com.example.petclinic.model.User;
import com.example.petclinic.service.impl.UserServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    private List<User> users;

    @BeforeEach
    public void initUsers() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .build();

        users = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");
        roles.add(role);
        
        User user = new User();
        user.setId(2);
        user.setUsername("testusername");
        user.setPassword("testpassword");
        user.setEnabled(true);
        user.setRoles(roles);
        users.add(user);
    }

    @Test
    void testGetUserSuccess() throws Exception{
        Mockito.when(this.userService.findById(2)).thenReturn(users.get(0));

        this.mockMvc.perform(get("/admin/users/2")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.username").value("testusername"));
    }

    @Test
    void testGetUserError() throws Exception{
        Mockito.when(this.userService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(get("/admin/users/-2")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllUsersSuccess() throws Exception{
        Mockito.when(this.userService.findAll()).thenReturn(users);

        this.mockMvc.perform(get("/admin/users/")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.[0].id").value(2))
        .andExpect(jsonPath("$.[0].username").value("testusername"));
    }

    @Test
    void testGetAllUsersError() throws Exception{
        Mockito.when(this.userService.findAll()).thenReturn(null);

        this.mockMvc.perform(get("/admin/users")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUserSuccess() throws Exception{
        User newUser = users.get(0);
        newUser.setId(null);

        ObjectMapper mapper = new ObjectMapper();
        String newVetAsJSON = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/admin/users/")
                .content(newVetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateUserError() throws Exception{
        User newUser = users.get(0);
        newUser.setId(999);

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJson = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/admin/users/")
                .content(newUserAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUserSuccess() throws Exception{
        Mockito.when(this.userService.findById(2)).thenReturn(users.get(0));

        User newUser = users.get(0);

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJson = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(put("/admin/users/2")
                .content(newUserAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/admin/users/2")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value(users.get(0).getUsername()));
    }

    @Test
    void testUpdateUserError() throws Exception{

        Mockito.when(this.userService.findById(2)).thenReturn(users.get(0));

        User newUser = users.get(0);
        newUser.setId(0);

        ObjectMapper mapper = new ObjectMapper();
        String newUserAsJson = mapper.writeValueAsString(newUser);

        this.mockMvc.perform(put("/admin/users/2")
                .content(newUserAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteUserSuccess() throws Exception{
        User deleteUser = users.get(0);

        ObjectMapper mapper = new ObjectMapper();
        String deleteUserAsJson = mapper.writeValueAsString(deleteUser);

        Mockito.when(this.userService.findById(2)).thenReturn(users.get(0));

        this.mockMvc.perform(delete("/admin/users/2")
                .content(deleteUserAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUserDeleteError() throws Exception{
        User deleteUser = users.get(0);

        ObjectMapper mapper = new ObjectMapper();
        String deleteUserAsJson = mapper.writeValueAsString(deleteUser);

        Mockito.when(this.userService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(delete("/admin/users/-1")
                .content(deleteUserAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}
