package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.File;
import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
         WHERE :#{#filter.id} IS NULL OR file.id = :#{#filter.id}
         OR (:#{#filter.rootPath} IS NULL OR file.rootPath = :#{#filter.rootPath})
         OR (:#{#filter.rootFilename} IS NULL OR file.rootFilename = :#{#filter.rootFilename})
         OR (:#{#filter.rootFullName} IS NULL OR file.rootFullName = :#{#filter.rootFullName})
         OR (:#{#filter.rootSize} IS NULL OR file.rootSize = :#{#filter.rootSize})
         OR (:#{#filter.rootFormat} IS NULL OR file.rootFormat = :#{#filter.rootFormat})
         OR (:#{#filter.displayPath} IS NULL OR file.displayPath = :#{#filter.displayPath})
         OR (:#{#filter.displayFilename} IS NULL OR file.displayFilename = :#{#filter.displayFilename})
         OR (:#{#filter.displayFullName} IS NULL OR file.displayFullName = :#{#filter.displayFullName})
         OR (:#{#filter.repository.id} IS NULL OR file.repository.id = :#{#filter.repository.id})
         OR (:#{#filter.accessTypes} IS NULL OR ARRAY_INTERSECT(file.accessTypes, :#{#filter.accessTypes}) IN :#{#filter.accessTypes})
         OR (:#{#filter.comments} IS NULL OR ARRAY_INTERSECT(file.comments, :#{#filter.comments}) IN :#{#filter.comments})
         OR (:#{#filter.secureCodes} IS NULL OR ARRAY_INTERSECT(file.secureCodes, :#{#filter.secureCodes}) IN :#{#filter.secureCodes})
        """)
    public List<File> findAllByFilter(@Param("filter") FileFilter filter);

    @Query(value = """
            SELECT file FROM File file
            WHERE file.rootFullName = :rootFullName
            """)
    public Optional<File> findByRootFullName(@Param("rootFullName") String rootFullName);
}
