package com.authentication.jwt.repository;

import com.authentication.jwt.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String role_employee);
}
