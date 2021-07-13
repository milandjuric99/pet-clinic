package com.example.petclinic.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.petclinic.PetClinicApplication;
import com.example.petclinic.controller.RoleController;
import com.example.petclinic.model.Role;
import com.example.petclinic.model.User;
import com.example.petclinic.service.impl.RoleServiceImpl;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = PetClinicApplication.class)
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private RoleController roleController;

    @MockBean
    private RoleServiceImpl roleService;

    private MockMvc mockMvc;

    private List<User> users;
    private Set<Role> roles;

    @BeforeEach
    public void initRoles(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .build();

        users = new ArrayList<>();
        roles = new HashSet<>();

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
    void testGetRolesSuccess() throws Exception{
        Mockito.when(this.roleService.findAll()).thenReturn(roles);

        this.mockMvc.perform(get("/admin/roles/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("ADMIN"));
    }

    @Test
    void testGetRolesError() throws Exception{
        Mockito.when(this.roleService.findAll()).thenReturn(null);

        this.mockMvc.perform(get("/admin/roles/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRoleSuccess() throws Exception{
        Role role = roles.stream().findAny().get();
        Mockito.when(this.roleService.findById(1)).thenReturn(role);

        this.mockMvc.perform(get("/admin/roles/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    void testGetRoleError() throws Exception{
        Mockito.when(this.roleService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(get("/api/pettypes/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateRole() throws Exception{
        Role newRole = roles.stream().findAny().get();
        newRole.setId(null);

        ObjectMapper mapper = new ObjectMapper();
        String newRoleAsJson = mapper.writeValueAsString(newRole);

        this.mockMvc.perform(post("/admin/roles/")
                .content(newRoleAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateRoleError() throws Exception{
        Role newRole = null;

        ObjectMapper mapper = new ObjectMapper();
        String newRoleAsJson = mapper.writeValueAsString(newRole);

        this.mockMvc.perform(post("/admin/roles/")
                .content(newRoleAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testDeleteRoleSuccess() throws Exception{
        Role deleteRole = roles.stream().findAny().get();
        ObjectMapper mapper = new ObjectMapper();
        String deleteRoleAsJson = mapper.writeValueAsString(deleteRole);
        Mockito.when(this.roleService.findById(2)).thenReturn(deleteRole);

        this.mockMvc.perform(delete("/admin/roles/2")
                .content(deleteRoleAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteRoleError() throws Exception{
        Role deleteRole = roles.stream().findAny().get();
        ObjectMapper mapper = new ObjectMapper();
        String deleteRoleAsJson = mapper.writeValueAsString(deleteRole);
        Mockito.when(this.roleService.findById(-1)).thenReturn(null);

        this.mockMvc.perform(delete("/api/pettypes/-1")
                .content(deleteRoleAsJson).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}
