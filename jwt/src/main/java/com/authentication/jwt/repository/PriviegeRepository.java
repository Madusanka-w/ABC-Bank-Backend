package com.authentication.jwt.repository;

import com.authentication.jwt.models.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriviegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
}
