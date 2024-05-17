package com.glos.api.authservice.service;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.dto.UserDetailsImpl;
import com.glos.api.authservice.exception.ResponseEntityException;
import com.glos.api.entities.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        ResponseEntity<User> response = userAPIClient.getByUsername(username);
        User u1 = getUser(response);
        User u2 = getUser(userDatabaseAPIClient.getById(u1.getId()));
        return new UserDetailsImpl(() -> u2);
    }

    private User getUser(ResponseEntity<User> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return response.getBody();
        } else if(response.getStatusCode().is5xxServerError()) {
            throw new RuntimeException("Internal server error");
        } else if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("Username not found");
        }
        throw new ResponseEntityException(response);
    }
}
