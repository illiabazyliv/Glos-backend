package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(value = """
            SELECT user.* FROM User user
            WHERE 
            """)
    List<User> findAllByFilter(UserFilter userFilter);
}
