package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = """
            SELECT user.* FROM User user
            WHERE user.id = :userFilter.id
            OR user.username = :userFilter.username
            OR user.email = :userFilter.email
            OR user.phone_number = :userFilter.phone_number
            OR user.gender = :userFilter.gender
            OR user.first_name = :userFilter.first_name
            OR user.last_name = :userFilter.last_name
            OR user.birthdate = :userFilter.birthdate
            OR user.is_account_non_expired = :userFilter.is_account_non_expired
            OR user.is_account_non_locked = :userFilter.is_account_non_locked
            OR user.is_credentials_non_expired = :userFilter.is_credentials_non_expired
            OR user.is_enabled = :userFilter.is_enabled
            OR  user.is_deleted = :userFilter.is_deleted
            OR user.roles = :userFilter.roles
            OR user.groups = :userFilter.groups
            """)
    List<User> findAllByFilter(UserFilter userFilter);
}
