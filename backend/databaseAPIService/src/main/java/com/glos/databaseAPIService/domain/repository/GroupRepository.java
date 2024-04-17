package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Group;
import com.glos.databaseAPIService.domain.filters.GroupFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>
{
    @Query(value = """
            SELECT group FROM Group group
            WHERE :#{#filter.id} IS NULL OR group.id = :#{#filter.id}
            AND :#{#filter.owner} IS NULL OR group.owner = :#{#filter.owner}
            AND (:#{#filter.users} IS NULL OR ARRAY_INTERSECT(group.users, :#{#filter.users}) IS NOT NULL)
            AND (:#{#filter.accessTypes} IS NULL OR ARRAY_INTERSECT(group.accessTypes, :#{#filter.accessTypes}) IS NOT NULL)
            """)
    List<Group> findAllByFilter(@Param("filter")GroupFilter filter);
}
