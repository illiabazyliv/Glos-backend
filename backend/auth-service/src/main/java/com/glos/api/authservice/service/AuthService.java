package com.glos.api.authservice.service;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.entities.Role;
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

    public AuthService(
            UserAPIClient userClient,
            PasswordEncoder passwordEncoder,
            JWTService jwtService
    ) {
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String create(User user, Role role) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        ResponseEntity<User> response = userClient.create(user, role.getName());
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
