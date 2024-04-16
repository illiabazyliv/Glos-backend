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

//        OR repository.rootPath = :filter.rootPath
//        OR repository.rootName = :filter.rootName
//        OR repository.rootFullName = :filter.rootFullName
//        OR repository.owner_id = :filter.owner_id
//        OR repository.isDefault = :filter.isDefault
//        OR repository.displayPath = :filter.displayPath
//        OR repository.displayName = :filter.displayName
//        OR repository.displayFullName = :filter.displayFullName
//        OR repository.description = :filter.description
//        OR repository.accessTypes = :filter.accessTypes
//        OR repository.comments = :filter.comments
//        OR repository.secureCodes = :filter.secureCodes
//        OR repository.tags = :filter.tags
//        OR repository.files = :filter.files

        @Query(value = """
                SELECT repository FROM Repository repository
                WHERE :#{#filter.id} IS NULL OR repository.id = :#{#filter.id}
                OR :#{#filter.rootPath} IS NULL OR repository.rootPath = :#{#filter.rootPath}
                OR :#{#filter.rootName} IS NULL OR repository.rootName = :#{#filter.rootName}
                OR :#{#filter.rootFullName} IS NULL OR repository.rootFullName = :#{#filter.rootFullName}
                OR :#{#filter.owner.id} IS NULL OR repository.owner.id = :#{#filter.owner.id}
                OR :#{#filter.isDefault} IS NULL OR repository.isDefault = :#{#filter.isDefault}
                OR :#{#filter.displayPath} IS NULL OR repository.displayPath = :#{#filter.displayPath}
                OR :#{#filter.displayName} IS NULL OR repository.displayName = :#{#filter.displayName}
                OR :#{#filter.displayFullName} IS NULL OR repository.displayFullName = :#{#filter.displayFullName}
                OR :#{#filter.description} IS NULL OR repository.description = :#{#filter.description}
                OR (:#{#filter.accessTypes} IS NULL OR ARRAY_INTERSECT(repository.accessTypes, :#{#filter.accessTypes}) IN :#{#filter.accessTypes})
                OR (:#{#filter.comments} IS NULL OR ARRAY_INTERSECT(repository.comments, :#{#filter.comments}) IN :#{#filter.comments})
                OR (:#{#filter.secureCodes} IS NULL OR ARRAY_INTERSECT(repository.secureCodes, :#{#filter.secureCodes}) IN :#{#filter.secureCodes})
                OR (:#{#filter.tags} IS NULL OR ARRAY_INTERSECT(repository.tags, :#{#filter.tags}) IN :#{#filter.tags})
                OR (:#{#filter.files} IS NULL OR ARRAY_INTERSECT(repository.files, :#{#filter.files}) IN :#{#filter.files})  
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
