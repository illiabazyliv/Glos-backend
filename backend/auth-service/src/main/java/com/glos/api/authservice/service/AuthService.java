package com.glos.api.authservice.service;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.entities.User;
import org.springframework.http.ResponseEntity;
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

    public String create(User user) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        ResponseEntity<User> response = userClient.create(user);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Faild created user");
        }
        return "";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public User getByUsername(String username) {
        ResponseEntity<User> response = userClient.getByUsername(username);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("User not found");
        }
        return response.getBody();
    }

}
