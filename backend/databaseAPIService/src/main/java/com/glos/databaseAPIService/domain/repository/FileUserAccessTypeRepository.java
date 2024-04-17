package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.FileUserAccessType;
import com.glos.databaseAPIService.domain.filters.FileUserAccessTypeFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUserAccessTypeRepository extends JpaRepository<FileUserAccessType, Long>
{
    @Query(value = """
            SELECT fileUserAccessType FROM FileUserAccessType fileUserAccessType
            WHERE :#{#filter.id} IS NULL OR fileUserAccessType.id = :#{#filter.id}
            AND :#{#filter.file} IS NULL OR fileUserAccessType.file = :#{#filter.file}
            AND :#{#filter.user} IS NULL OR fileUserAccessType.user = :#{#filter.user}
            AND :#{#filter.accessType} IS NULL OR fileUserAccessType.accessType = :#{#filter.accessType}
            """)
    List<FileUserAccessType> findAllByFilter(@Param("filter") FileUserAccessTypeFilter filter);
}
