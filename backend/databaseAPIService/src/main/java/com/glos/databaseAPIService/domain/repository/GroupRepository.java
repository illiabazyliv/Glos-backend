package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
