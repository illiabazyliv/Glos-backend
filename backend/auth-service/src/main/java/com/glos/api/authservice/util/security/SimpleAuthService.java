package com.glos.api.authservice.util.security;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.dto.SignInRequest;
import com.glos.api.authservice.exception.UserAccountStateException;
import com.glos.api.authservice.mapper.JwtEntityMapper;
import com.glos.api.entities.User;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class SimpleAuthService implements AuthService {

    private final UserAPIClient userAPIClient;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final JwtEntityMapper jwtEntityMapper;

    public SimpleAuthService(
            UserAPIClient userAPIClient,
            JwtService jwtService,
            AuthenticationManager authManager,
            JwtEntityMapper jwtEntityMapper
    ) {
        this.userAPIClient = userAPIClient;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.jwtEntityMapper = jwtEntityMapper;
    }

    @Override
    public JwtResponse authenticate(JwtRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            throw new IllegalStateException("Authentication is already done.");
        }

        User user = getUserByUsername(request.getUsername());

        if (user.getIs_account_non_locked() != null && !user.getIs_account_non_locked()) {
            throw new UserAccountStateException("Account is blocked.");
        }

        JwtEntity entity = jwtEntityMapper.toDto(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                entity, entity.getPassword(), entity.getAuthorities()
        );
        System.out.println(authentication);
        authManager.authenticate(authentication);

        return createResponse(user);
    }

    private User getUserByUsername(String username) {
        ResponseEntity<User> response = userAPIClient.getByUsername(username);

        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
            throw new UsernameNotFoundException("User not found");
        }

        return response.getBody();
    }

    private JwtResponse createResponse(User user) {
        JwtEntity jwtEntity = jwtEntityMapper.toDto(user);

        String accessToken = jwtService.generateAccessToken(jwtEntity);
        String refreshToken = jwtService.generateRefreshToken(jwtEntity);

        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtService.refreshUserTokens(refreshToken);
    }

    @Override
    public JwtResponse validate(String token) {
        return null;
    }
}
