package com.unriviel.api.service.impl;

import com.unriviel.api.model.Role;
import com.unriviel.api.model.RoleName;
import com.unriviel.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Find all roles from the database
     */
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
    public Optional<Role> finByRoleName(RoleName roleName){
        return  roleRepository.findByRole(roleName);
    }
    public Role create(Role role){
         return roleRepository.save(role);
    }

}
