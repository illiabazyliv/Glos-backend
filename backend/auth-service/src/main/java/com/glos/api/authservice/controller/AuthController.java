package com.glos.api.authservice.controller;

import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.dto.SignInRequest;
import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.service.AuthService;
import com.glos.api.entities.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SignUpRequestMapper signUpRequestMapper;

    private final UserDatabaseAPIClient userDatabaseAPIClient;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            AuthService authService,
            SignUpRequestMapper signUpRequestMapper,
            UserDatabaseAPIClient userDatabaseAPIClient,
            AuthenticationManager authenticationManager
    ) {
        this.authService = authService;
        this.signUpRequestMapper = signUpRequestMapper;
        this.userDatabaseAPIClient = userDatabaseAPIClient;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<User>> get() {
        return userDatabaseAPIClient.getAll();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody SignUpRequest request
    ) {
        User user = signUpRequestMapper.toEntity(request);
        return ResponseEntity.ok(authService.create(user));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(
            @RequestBody SignUpRequest request
    ) {
        User user = signUpRequestMapper.toEntity(request);
        return ResponseEntity.ok(authService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody SignInRequest signInRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(
                    authService.generateToken(signInRequest.getUsername())
            );
        } else {
            throw new UsernameNotFoundException("invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(
            @RequestParam("token") String token
    ) {
        authService.validateToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        return ResponseEntity.ok(null);
    }
}
