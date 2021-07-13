package com.example.petclinic.service;

import com.example.petclinic.model.Role;

import java.util.Collection;

public interface RoleService {

    Collection<Role> findAll();
    Role findById(Integer roleId);
    void save(Role role);
    void delete(Role role);
}
