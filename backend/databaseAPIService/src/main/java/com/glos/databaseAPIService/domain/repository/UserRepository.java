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
            OR :#{#userFilter.username} IS NULL OR user.username = :#{#userFilter.username}
            OR :#{#userFilter.email} IS NULL OR user.email = :#{#userFilter.email}
            OR :#{#userFilter.phone_number} IS NULL OR user.phone_number = :#{#userFilter.phone_number}
            OR :#{#userFilter.gender} IS NULL OR user.gender = :#{#userFilter.gender}
            OR :#{#userFilter.first_name} IS NULL OR user.first_name = :#{#userFilter.first_name}
            OR :#{#userFilter.last_name} IS NULL OR user.last_name = :#{#userFilter.last_name}
            OR :#{#userFilter.birthdate} IS NULL OR user.birthdate = :#{#userFilter.birthdate}
            OR :#{#userFilter.is_account_non_expired} IS NULL OR user.is_account_non_expired = :#{#userFilter.is_account_non_expired}
            OR :#{#userFilter.is_account_non_locked} IS NULL OR user.is_account_non_locked = :#{#userFilter.is_account_non_locked}
            OR :#{#userFilter.is_credentials_non_expired} IS NULL OR user.is_credentials_non_expired = :#{#userFilter.is_credentials_non_expired}
            OR :#{#userFilter.is_enabled} IS NULL OR user.is_enabled = :#{#userFilter.is_enabled}
            OR :#{#userFilter.is_deleted} IS NULL OR user.is_deleted = :#{#userFilter.is_deleted}
            OR (:#{#userFilter.roles} IS NULL OR ARRAY_INTERSECT(user.roles, :#{#userFilter.roles}) IN :#{#userFilter.roles})
            OR (:#{#userFilter.groups} IS NULL OR ARRAY_INTERSECT(user.groups, :#{#userFilter.groups}) IN :#{#userFilter.groups})
            """)
    public List<User> findAllByFilter(@Param("userFilter") UserFilter userFilter);

//    OR user.username = :userFilter.username
//    OR user.email = :userFilter.email
//    OR user.phone_number = :userFilter.phone_number
//    OR user.gender = :userFilter.gender
//    OR user.first_name = :userFilter.first_name
//    OR user.last_name = :userFilter.last_name
//    OR user.birthdate = :userFilter.birthdate
//    OR user.is_account_non_expired = :userFilter.is_account_non_expired
//    OR user.is_account_non_locked = :userFilter.is_account_non_locked
//    OR user.is_credentials_non_expired = :userFilter.is_credentials_non_expired
//    OR user.is_enabled = :userFilter.is_enabled
//    OR  user.is_deleted = :userFilter.is_deleted
//    OR user.roles = :userFilter.roles
//    OR user.groups = :userFilter.groups
}
