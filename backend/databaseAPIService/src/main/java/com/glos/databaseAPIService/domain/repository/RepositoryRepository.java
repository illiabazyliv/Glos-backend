    package com.glos.databaseAPIService.domain.repository;

    import com.glos.databaseAPIService.domain.entity.Repository;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;

    public interface RepositoryRepository extends JpaRepository<Repository, Long>
    {
        @Query(value = """
                """)
        List<Repository> findAllByFilter();

        @Query(value = """
                SELECT * FROM repositories
                WHERE owner_id = :id
                """)
        List<Repository> findByOwnerId(@Param("id") Long id);
    }
