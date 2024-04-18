package com.glos.databaseAPIService.domain.repository;

import com.glos.api.entities.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface RepositoryUserAccessTypeRepository extends JpaRepository<RepositoryUserAccessType, Long>
{
    @Query(value = """
            SELECT rep FROM RepositoryUserAccessType rep
            WHERE :#{#filter.asMap().id} IS NULL OR rep.id = :#{#filter.asMap().get("id")}
            AND :#{#filter.asMap().repository} IS NULL OR rep.repository = :#{#filter.asMap().get("repository")}
            AND :#{#filter.asMap().user} IS NULL OR rep.user = :#{#filter.asMap().get("user")}
            AND :#{#filter.asMap().accessType} IS NULL OR rep.accessType = :#{#filter.asMap().get("accessType")}
            """)
    List<RepositoryUserAccessType> findAllByFilter(@Param("filter") EntityFilter filter);
}

