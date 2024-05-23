package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entities.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType, Long>
{
    Optional<AccessType> findByName(String name);
}
