package com.glos.databaseAPIService.domain.repository;


import com.glos.api.entities.File;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long>
{
    @Query(value = """
            SELECT file FROM File file
            WHERE file.repository.id = :repositoryId
            """)
    public List<File> findAllByRepositoryId(@Param("repositoryId") Long repositoryId);

@Query("""
         SELECT file FROM File file
         WHERE :#{#filter.asMap().id} IS NULL OR file.id = :#{#filter.asMap().id}
         AND (:#{#filter.asMap().rootPath} IS NULL OR file.rootPath = :#{#filter.asMap().rootPath})
         AND (:#{#filter.asMap().rootFilename} IS NULL OR file.rootFilename = :#{#filter.asMap().rootFilename})
         AND (:#{#filter.asMap().rootFullName} IS NULL OR file.rootFullName = :#{#filter.asMap().rootFullName})
         AND (:#{#filter.asMap().rootSize} IS NULL OR file.rootSize = :#{#filter.asMap().rootSize})
         AND (:#{#filter.asMap().rootFormat} IS NULL OR file.rootFormat = :#{#filter.asMap().rootFormat})
         AND (:#{#filter.asMap().displayPath} IS NULL OR file.displayPath = :#{#filter.asMap().displayPath})
         AND (:#{#filter.asMap().displayFilename} IS NULL OR file.displayFilename = :#{#filter.asMap().displayFilename})
         AND (:#{#filter.asMap().displayFullName} IS NULL OR file.displayFullName = :#{#filter.asMap().displayFullName})
         AND (:#{#filter.asMap().repository.id} IS NULL OR file.repository.id = :#{#filter.asMap().repository.id})
         AND (:#{#filter.asMap().accessTypes} IS NULL OR ARRAY_INTERSECT(file.accessTypes, :#{#filter.asMap().accessTypes})  IS NOT NULL)
         AND (:#{#filter.asMap().comments} IS NULL OR ARRAY_INTERSECT(file.comments, :#{#filter.asMap().comments})  IS NOT NULL)
         AND (:#{#filter.asMap().secureCodes} IS NULL OR ARRAY_INTERSECT(file.secureCodes, :#{#filter.asMap().secureCodes})  IS NOT NULL)
        """)
    public List<File> findAllByFilter(@Param("filter") EntityFilter filter);

    @Query(value = """
            SELECT file FROM File file
            WHERE file.rootFullName = :rootFullName
            """)
    public Optional<File> findByRootFullName(@Param("rootFullName") String rootFullName);
}
