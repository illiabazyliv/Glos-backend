package com.glos.api.authservice.util.security;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.exception.UserAccountStateException;
import com.glos.api.authservice.shared.SharedEntity;
import com.glos.api.entities.Roles;
import com.glos.api.entities.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SimpleAuthService implements AuthService {

    private final UserAPIClient userAPIClient;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SimpleAuthService(
            UserAPIClient userAPIClient,
            JwtService jwtService,
            AuthenticationManager authManager,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.userAPIClient = userAPIClient;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtResponse register(JwtEntity jwtEntity) {
        JwtRequest request = new JwtRequest(jwtEntity.getUsername(), jwtEntity.getPassword());
        User user = jwtEntity.getUser();
        create(user, Roles.fromName(user.getRoles().get(0).getName()));
        return authenticate(request);
    }

    private User create(User user, Roles role) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        ResponseEntity<User> response = userAPIClient.create(user, role.name());
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Faild created user");
        }
        User created = response.getBody();
        created.setPassword_hash(user.getPassword_hash());
        return created;
    }

    @Override
    public JwtResponse authenticate(JwtRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );

        authentication = authManager.authenticate(authentication);

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

        return createResponse(user);
    }

    private User getUserByUsername(String username) {
        ResponseEntity<User> response = userAPIClient.getByUsername(username);

        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("User not found");
        }

        return response.getBody();
    }

    private JwtResponse createResponse(UserDetails user) {
        JwtEntity jwtEntity = (JwtEntity) user;

        String accessToken = jwtService.generateAccessToken(jwtEntity);
        String refreshToken = jwtService.generateRefreshToken(jwtEntity);

        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public JwtResponse refresh(JwtRefreshRequest refreshRequest) {
        return jwtService.refreshUserTokens(refreshRequest.getRefreshToken());
    }

    @Override
    public boolean validate(String token) {
        return jwtService.validateToken(token);
    }
}
