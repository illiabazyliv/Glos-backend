package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.FIleUserAccessType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUserAccessTypeRepository extends JpaRepository<FIleUserAccessType, Long> {
}
