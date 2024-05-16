package com.glos.api.authservice.controller;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.service.AuthService;
import com.glos.api.authservice.util.Constants;
import com.glos.api.authservice.util.security.JwtRequest;
import com.glos.api.authservice.util.security.JwtResponse;
import com.glos.api.authservice.util.security.SimpleAuthService;
import com.glos.api.authservice.validation.OnCreate;
import com.glos.api.entities.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SignUpRequestMapper signUpRequestMapper;
    private final AuthenticationManager authenticationManager;
    private final UserAPIClient userAPIClient;
    private final SimpleAuthService simpleAuthService;

    public AuthController(
            AuthService authService,
            SignUpRequestMapper signUpRequestMapper,
            UserAPIClient userAPIClient,
            AuthenticationManager authenticationManager,
            SimpleAuthService simpleAuthService
    ) {
        this.authService = authService;
        this.signUpRequestMapper = signUpRequestMapper;
        this.userAPIClient = userAPIClient;
        this.authenticationManager = authenticationManager;
        this.simpleAuthService = simpleAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(
            @RequestBody  @Validated(OnCreate.class)  SignUpRequest request
    ) {
        User user = signUpRequestMapper.toEntity(request);
        JwtResponse response = new JwtResponse();
        response.setAccessToken(authService.create(user, Constants.ROLE_USER));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<JwtResponse> registerAdmin(
            @RequestBody @Validated(OnCreate.class) SignUpRequest request
    ) {
        User user = signUpRequestMapper.toEntity(request);
        JwtResponse response = new JwtResponse();
        response.setAccessToken(authService.create(user, Constants.ROLE_ADMIN));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid JwtRequest jwtRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            //TokenResponse response = new TokenResponse();
            JwtResponse response = new JwtResponse();
            response.setAccessToken(authService.generateToken(jwtRequest.getUsername()));

            //JwtResponse response = simpleAuthService.authenticate(jwtRequest);
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestParam("token") String token
    ) {
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh() {
        JwtResponse response = new JwtResponse();
        return ResponseEntity.ok(response);
    }
}
