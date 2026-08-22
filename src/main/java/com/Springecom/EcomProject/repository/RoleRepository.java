package com.Springecom.EcomProject.repository;

import com.Springecom.EcomProject.model.Role;
import com.Springecom.EcomProject.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
