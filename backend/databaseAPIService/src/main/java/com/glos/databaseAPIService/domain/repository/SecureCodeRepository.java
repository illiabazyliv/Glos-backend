package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.SecureCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureCodeRepository extends JpaRepository<SecureCode, Long> {
}
