package com.glos.databaseAPIService.domain.repository;

import com.glos.api.entities.SecureCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface SecureCodeRepository extends JpaRepository<SecureCode, Long> {}
