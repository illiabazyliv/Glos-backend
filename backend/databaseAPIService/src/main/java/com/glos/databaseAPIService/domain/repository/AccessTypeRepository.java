package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType, Long>
{
    List<AccessType> findByName(String name);
}
