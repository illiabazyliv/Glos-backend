package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.SecureCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureCodeRepository extends JpaRepository<SecureCode, Long> {}
