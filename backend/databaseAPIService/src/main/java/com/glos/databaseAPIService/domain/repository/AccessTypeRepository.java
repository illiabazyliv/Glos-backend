package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTypeRepository extends JpaRepository<AccessType, Long> {
}
