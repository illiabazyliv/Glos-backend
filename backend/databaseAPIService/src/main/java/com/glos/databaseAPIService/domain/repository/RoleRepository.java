package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Optional<Role> findByName(String name);
}
