    package com.glos.databaseAPIService.domain.repository;

    import com.glos.databaseAPIService.domain.entity.Repository;
    import com.glos.databaseAPIService.domain.entity.Tag;
    import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;

    @org.springframework.stereotype.Repository
    public interface RepositoryRepository extends JpaRepository<Repository, Long>
    {
        @Query(value = """
                SELECT repository.* FROM Repository repository
                WHERE repository.id = :filter.id
                OR repository.rootPath = :filter.rootPath
                OR repository.rootName = :filter.rootName
                OR repository.rootFullName = :filter.rootFullName
                OR repository.owner_id = :filter.owner_id
                OR repository.isDefault = :filter.isDefault
                OR repository.displayPath = :filter.displayPath
                OR repository.displayName = :filter.displayName
                OR repository.displayFullName = :filter.displayFullName
                OR repository.description = :filter.description
                OR repository.accessTypes = :filter.accessTypes
                OR repository.comments = :filter.comments
                OR repository.secureCodes = :filter.secureCodes
                OR repository.tags = :filter.tags
                OR repository.files = :filter.files
                """)
        List<Repository> findAllByFilter(@Param("filter") RepositoryFilter filter);

        @Query(value = """
                SELECT * FROM repositories
                WHERE owner_id = :id
                """)
        List<Repository> findByOwnerId(@Param("id") Long id);

        @Query(value = """
                SELECT * FROM repositories
                WHERE root_full_name = :rootFullName
                """)
        Optional<Repository> findByRootFullName(@Param("rootFullName") String rootFullName);
    }
