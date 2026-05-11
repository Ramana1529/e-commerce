package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Role;
import com.ecommerce.project.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}