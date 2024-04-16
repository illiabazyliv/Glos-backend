package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.RepositoryUserAccessType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUserAccessTypeRepository extends JpaRepository<RepositoryUserAccessType, Long> {
}
