package com.example.petclinic.controller;

import com.example.petclinic.model.Role;
import com.example.petclinic.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/admin/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Collection<Role>> getRoles(){

        Collection<Role> roles = this.roleService.findAll();
        if(roles == null){
            return new ResponseEntity<Collection<Role>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Collection<Role>>(roles, HttpStatus.OK);
    }

    @GetMapping(value = "/{roleId}", produces = "application/json")
    public ResponseEntity<Role> getRole(@PathVariable("roleId") Integer roleId){

        Role role = this.roleService.findById(roleId);
        if(role == null){
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Role>(role, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Role> addRole(@RequestBody Role role, UriComponentsBuilder ucBuilder){

        HttpHeaders headers = new HttpHeaders();
        if(role == null){
            return new ResponseEntity<Role>(HttpStatus.NOT_FOUND);
        }
        this.roleService.save(role);
        headers.setLocation(ucBuilder.path("/admin/roles/{roleId}").buildAndExpand(role.getId()).toUri());
        return new ResponseEntity<Role>(role, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{roleId}", produces = "application/json")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Integer roleId){

        Role role = this.roleService.findById(roleId);
        if(role == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
