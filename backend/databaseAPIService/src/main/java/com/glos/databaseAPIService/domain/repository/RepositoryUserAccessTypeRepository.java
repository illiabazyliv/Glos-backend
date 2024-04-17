package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.filters.RepositoryUserAccessTypeFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryUserAccessTypeRepository extends JpaRepository<RepositoryUserAccessType, Long>
{
    @Query(value = """
            SELECT rep FROM RepositoryUserAccessType rep
            WHERE :#{#filter.id} IS NULL OR rep.id = :#{#filter.id}
            AND :#{#filter.repository} IS NULL OR rep.repository = :#{#filter.repository}
            AND :#{#filter.user} IS NULL OR rep.user = :#{#filter.user}
            AND :#{#filter.accessType} IS NULL OR rep.accessType = :#{#filter.accessType}
            """)
    List<RepositoryUserAccessType> findAllByFilter(@Param("filter") RepositoryUserAccessTypeFilter filter);
}

