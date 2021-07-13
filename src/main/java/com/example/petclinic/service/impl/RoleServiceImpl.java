package com.example.petclinic.service.impl;

import com.example.petclinic.model.Role;
import com.example.petclinic.repository.RoleRepository;
import com.example.petclinic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role findById(Integer roleId) {
        return this.roleRepository.findById(roleId).get();
    }

    @Override
    public void save(Role role) {
        this.roleRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        this.roleRepository.delete(role);
    }
}
