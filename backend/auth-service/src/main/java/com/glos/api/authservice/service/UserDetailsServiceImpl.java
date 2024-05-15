package com.glos.api.authservice.service;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.dto.UserDetailsImpl;
import com.glos.api.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAPIClient userAPIClient;
    private final UserDatabaseAPIClient userDatabaseAPIClient;

    public UserDetailsServiceImpl(
            UserAPIClient userAPIClient,
            UserDatabaseAPIClient userDatabaseAPIClient
    ) {
        this.userAPIClient = userAPIClient;
        this.userDatabaseAPIClient = userDatabaseAPIClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDatabaseAPIClient
                .getByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
