package com.glos.databaseAPIService.domain.repository;

import com.accesstools.AccessNode;
import com.glos.databaseAPIService.domain.entities.AccessType;
import com.glos.databaseAPIService.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType, Long>
{
    default Map.Entry<AccessType, Boolean> ensureByName(String name) {
        final Optional<AccessType> accessTypeOpt = findByName(name);
        final AccessNode node = AccessNode.builder(name).build();
        return Map.entry(accessTypeOpt.orElseGet(() -> save(new AccessType(null, node.getPattern()))), accessTypeOpt.isEmpty());
    }

    Optional<AccessType> findByName(String name);
}
