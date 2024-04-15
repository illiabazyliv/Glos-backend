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


    @Query(value = """
            SELECT file FROM File file
            WHERE file.id = :filter.id
            OR file.rootPath = :filter.rootPath
            OR file.rootFilename = :filter.rootFilename
            OR file.rootFullName = :filter.rootFullName
            OR file.rootSize = :filter.rootSize
            OR file.rootFormat = :filter.rootFormat
            OR file.displayPath = :filter.displayPath
            OR file.displayFilename = :filter.displayFilename
            OR file.displayFullName = :filter.displayFullName
            OR file.repository = :filter.repository
            OR file.accessTypes = :filter.accessTypes
            OR file.comments = :filter.comments
            OR file.secureCodes = :filter.secureCodes
            """)
    public List<File> findAllByFilter(@Param("filter") FileFilter filter);

    @Query(value = """
            SELECT * FROM files
            WHERE root_full_name = :rootFullName
            """)
    public Optional<File> findByRootFullName(@Param("rootFullName") String rootFullName);
}
