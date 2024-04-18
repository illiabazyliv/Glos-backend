package com.glos.databaseAPIService.domain.repository;


import com.glos.api.entities.User;
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
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query(value = """
            SELECT user FROM User user
            WHERE user.phone_number = :phoneNumber
            """)
    Optional<User> findByPhoneNumber(@Param("phoneNumber")String phoneNumber);

    @Query(value = """
            SELECT user FROM User user
            WHERE :#{#filter.asMap().id} IS NULL OR user.id = :#{#filter.asMap().id}
            AND :#{#filter.asMap().username} IS NULL OR user.username = :#{#filter.asMap().username}
            AND :#{#filter.asMap().email} IS NULL OR user.email = :#{#filter.asMap().email}
            AND :#{#filter.asMap().phone_number} IS NULL OR user.phone_number = :#{#filter.asMap().phone_number}
            AND :#{#filter.asMap().gender} IS NULL OR user.gender = :#{#filter.asMap().gender}
            AND :#{#filter.asMap().first_name} IS NULL OR user.first_name = :#{#filter.asMap().first_name}
            AND :#{#filter.asMap().last_name} IS NULL OR user.last_name = :#{#filter.asMap().last_name}
            AND :#{#filter.asMap().birthdate} IS NULL OR user.birthdate = :#{#filter.asMap().birthdate}
            AND :#{#filter.asMap().is_account_non_expired} IS NULL OR user.is_account_non_expired = :#{#filter.asMap().is_account_non_expired}
            AND :#{#filter.asMap().is_account_non_locked} IS NULL OR user.is_account_non_locked = :#{#filter.asMap().is_account_non_locked}
            AND :#{#filter.asMap().is_credentials_non_expired} IS NULL OR user.is_credentials_non_expired = :#{#filter.asMap().is_credentials_non_expired}
            AND :#{#filter.asMap().is_enabled} IS NULL OR user.is_enabled = :#{#filter.asMap().is_enabled}
            AND :#{#filter.asMap().is_deleted} IS NULL OR user.is_deleted = :#{#filter.asMap().is_deleted}
            AND (:#{#filter.asMap().roles} IS NULL OR ARRAY_INTERSECT(user.roles, :#{#filter.asMap().roles}) IS NOT NULL)
            AND (:#{#filter.asMap().groups} IS NULL OR ARRAY_INTERSECT(user.groups, :#{#filter.asMap().groups}) IS NOT NULL)
            """)
    public List<User> findAllByFilter(@Param("filter") EntityFilter filter);

}
