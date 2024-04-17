    package com.glos.databaseAPIService.domain.repository;

    import com.glos.databaseAPIService.domain.entity.Repository;
    import com.glos.databaseAPIService.domain.entity.Tag;
    import com.glos.databaseAPIService.domain.filters.EntityFilter;
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
                WHERE :#{#filter.asMap().id} IS NULL OR repository.id = :#{#filter.asMap().id}
                AND :#{#filter.asMap().rootPath} IS NULL OR repository.rootPath = :#{#filter.asMap().rootPath}
                AND :#{#filter.asMap().rootName} IS NULL OR repository.rootName = :#{#filter.asMap().rootName}
                AND :#{#filter.asMap().rootFullName} IS NULL OR repository.rootFullName = :#{#filter.asMap().rootFullName}
                AND :#{#filter.asMap().owner.id} IS NULL OR repository.owner.id = :#{#filter.asMap().owner.id}
                AND :#{#filter.asMap().isDefault} IS NULL OR repository.isDefault = :#{#filter.asMap().isDefault}
                AND :#{#filter.asMap().displayPath} IS NULL OR repository.displayPath = :#{#filter.asMap().displayPath}
                AND :#{#filter.asMap().displayName} IS NULL OR repository.displayName = :#{#filter.asMap().displayName}
                AND :#{#filter.asMap().displayFullName} IS NULL OR repository.displayFullName = :#{#filter.asMap().displayFullName}
                AND :#{#filter.asMap().description} IS NULL OR repository.description = :#{#filter.asMap().description}
                AND (:#{#filter.asMap().accessTypes} IS NULL OR ARRAY_INTERSECT(repository.accessTypes, :#{#filter.asMap().accessTypes}) IS NOT NULL)
                AND (:#{#filter.asMap().comments} IS NULL OR ARRAY_INTERSECT(repository.comments, :#{#filter.asMap().comments})  IS NOT NULL)
                AND (:#{#filter.asMap().secureCodes} IS NULL OR ARRAY_INTERSECT(repository.secureCodes, :#{#filter.asMap().secureCodes})  IS NOT NULL)
                AND (:#{#filter.asMap().tags} IS NULL OR ARRAY_INTERSECT(repository.tags, :#{#filter.asMap().tags})  IS NOT NULL)
                AND (:#{#filter.asMap().files} IS NULL OR ARRAY_INTERSECT(repository.files, :#{#filter.asMap().files})  IS NOT NULL)
                """)
        public List<Repository> findAllByFilter(@Param("filter") EntityFilter filter);

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
