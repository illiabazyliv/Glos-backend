package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;

=======
/**
 * 	@author - yablonovskydima
 */
>>>>>>> 598b04d4c42ec0d109aac54ff9f8595880e639df
@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType, Long>
{
    Optional<AccessType> findByName(String name);
}
