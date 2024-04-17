    package com.glos.databaseAPIService.domain.repository;

    import com.glos.databaseAPIService.domain.entity.Repository;
    import com.glos.databaseAPIService.domain.entity.Tag;
    import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;
    /**
     * 	@author - yablonovskydima
     */
    @org.springframework.stereotype.Repository
    public interface RepositoryRepository extends JpaRepository<Repository, Long>
    {

        @Query(value = """
                SELECT repository FROM Repository repository
                WHERE :#{#filter.id} IS NULL OR repository.id = :#{#filter.id}
                AND :#{#filter.rootPath} IS NULL OR repository.rootPath = :#{#filter.rootPath}
                AND :#{#filter.rootName} IS NULL OR repository.rootName = :#{#filter.rootName}
                AND :#{#filter.rootFullName} IS NULL OR repository.rootFullName = :#{#filter.rootFullName}
                AND :#{#filter.owner.id} IS NULL OR repository.owner.id = :#{#filter.owner.id}
                AND :#{#filter.isDefault} IS NULL OR repository.isDefault = :#{#filter.isDefault}
                AND :#{#filter.displayPath} IS NULL OR repository.displayPath = :#{#filter.displayPath}
                AND :#{#filter.displayName} IS NULL OR repository.displayName = :#{#filter.displayName}
                AND :#{#filter.displayFullName} IS NULL OR repository.displayFullName = :#{#filter.displayFullName}
                AND :#{#filter.description} IS NULL OR repository.description = :#{#filter.description}
                AND (:#{#filter.accessTypes} IS NULL OR ARRAY_INTERSECT(repository.accessTypes, :#{#filter.accessTypes}) IS NOT NULL)
                AND (:#{#filter.comments} IS NULL OR ARRAY_INTERSECT(repository.comments, :#{#filter.comments})  IS NOT NULL)
                AND (:#{#filter.secureCodes} IS NULL OR ARRAY_INTERSECT(repository.secureCodes, :#{#filter.secureCodes})  IS NOT NULL)
                AND (:#{#filter.tags} IS NULL OR ARRAY_INTERSECT(repository.tags, :#{#filter.tags})  IS NOT NULL)
                AND (:#{#filter.files} IS NULL OR ARRAY_INTERSECT(repository.files, :#{#filter.files})  IS NOT NULL)
                """)
        public List<Repository> findAllByFilter(@Param("filter") RepositoryFilter filter);

        @Query(value = """
                SELECT repository FROM Repository repository
                WHERE repository.owner.id = :id
                """)
        List<Repository> findByOwnerId(@Param("id") Long id);

        @Query(value = """
                SELECT repository FROM Repository repository
                WHERE repository.rootFullName = :rootFullName
                """)
        Optional<Repository> findByRootFullName(@Param("rootFullName") String rootFullName);
    }
