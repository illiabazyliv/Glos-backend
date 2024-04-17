package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
            WHERE :#{#userFilter.id} IS NULL OR user.id = :#{#userFilter.id}
            AND :#{#userFilter.username} IS NULL OR user.username = :#{#userFilter.username}
            AND :#{#userFilter.email} IS NULL OR user.email = :#{#userFilter.email}
            AND :#{#userFilter.phone_number} IS NULL OR user.phone_number = :#{#userFilter.phone_number}
            AND :#{#userFilter.gender} IS NULL OR user.gender = :#{#userFilter.gender}
            AND :#{#userFilter.first_name} IS NULL OR user.first_name = :#{#userFilter.first_name}
            AND :#{#userFilter.last_name} IS NULL OR user.last_name = :#{#userFilter.last_name}
            AND :#{#userFilter.birthdate} IS NULL OR user.birthdate = :#{#userFilter.birthdate}
            AND :#{#userFilter.is_account_non_expired} IS NULL OR user.is_account_non_expired = :#{#userFilter.is_account_non_expired}
            AND :#{#userFilter.is_account_non_locked} IS NULL OR user.is_account_non_locked = :#{#userFilter.is_account_non_locked}
            AND :#{#userFilter.is_credentials_non_expired} IS NULL OR user.is_credentials_non_expired = :#{#userFilter.is_credentials_non_expired}
            AND :#{#userFilter.is_enabled} IS NULL OR user.is_enabled = :#{#userFilter.is_enabled}
            AND :#{#userFilter.is_deleted} IS NULL OR user.is_deleted = :#{#userFilter.is_deleted}
            AND (:#{#userFilter.roles} IS NULL OR ARRAY_INTERSECT(user.roles, :#{#userFilter.roles}) IS NOT NULL)
            AND (:#{#userFilter.groups} IS NULL OR ARRAY_INTERSECT(user.groups, :#{#userFilter.groups}) IS NOT NULL)
            """)
    public List<User> findAllByFilter(@Param("userFilter") UserFilter userFilter);

}
