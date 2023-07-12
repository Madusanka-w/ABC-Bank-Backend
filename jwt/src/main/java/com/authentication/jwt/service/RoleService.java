package com.authentication.jwt.service;

import com.authentication.jwt.models.entities.Role;
import com.authentication.jwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role createRoleByName (String roleName){


//        Role role = new Role(roleName);
//
//        role.setPrivileges(new ArrayList<>());
//        role.setUsers(new ArrayList<>());
//
//        return roleRepository.save(role);

        return new Role();
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role create(String roleName) {
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }
}
