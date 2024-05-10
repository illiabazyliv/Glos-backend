package com.glos.api.authservice.service;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserAPIClient userClient;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserDatabaseAPIClient userDatabaseAPIClient;

    public AuthService(
            UserAPIClient userClient,
            PasswordEncoder passwordEncoder,
            JWTService jwtService,
            UserDetailsService userDetailsService,
            UserDatabaseAPIClient userDatabaseAPIClient
    ) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userDatabaseAPIClient = userDatabaseAPIClient;
    }

    public String create(User user) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        ResponseEntity<User> response = userDatabaseAPIClient.create(user);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Faild created user");
        }
        User created = response.getBody();
        return generateToken(created);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public String generateToken(User user) {
        return jwtService.generateToken(user.getUsername());
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
