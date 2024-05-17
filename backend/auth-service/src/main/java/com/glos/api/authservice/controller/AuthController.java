package com.glos.api.authservice.controller;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.service.AuthService;
import com.glos.api.authservice.util.Constants;
import com.glos.api.authservice.util.security.*;
import com.glos.api.authservice.validation.OnCreate;
import com.glos.api.entities.User;
import jakarta.validation.Valid;
import com.glos.api.authservice.dto.SignInRequest;
import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.service.AuthService;
import com.glos.api.entities.Roles;
import com.glos.api.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final SignUpRequestMapper signUpRequestMapper;
    private final SimpleAuthService simpleAuthService;

    public AuthController(
            SignUpRequestMapper signUpRequestMapper,
            SimpleAuthService simpleAuthService
    ) {
        this.signUpRequestMapper = signUpRequestMapper;
        this.simpleAuthService = simpleAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(
            @RequestBody  @Validated(OnCreate.class)  SignUpRequest request
    ) {
        JwtEntity jwtEntity = new JwtEntity(() -> {
            User user = signUpRequestMapper.toEntity(request);
            user.setRoles(Collections.singletonList(Roles.ROLE_USER.asEntity()));
            return user;
        });
        JwtResponse response = simpleAuthService.register(jwtEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<JwtResponse> registerAdmin(
            @RequestBody @Validated(OnCreate.class) SignUpRequest request
    ) {
        JwtEntity jwtEntity = new JwtEntity(() -> {
            User user = signUpRequestMapper.toEntity(request);
            user.setRoles(Collections.singletonList(Roles.ROLE_ADMIN.asEntity()));
            return user;
        });
        JwtResponse response = simpleAuthService.register(jwtEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid JwtRequest jwtRequest
    ) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            JwtResponse response = new JwtResponse();
//            response.setAccessToken(authService.generateToken(jwtRequest.getUsername()));
//
////            JwtResponse response = simpleAuthService.authenticate(jwtRequest);
//            return ResponseEntity.ok(response);
//        }
//        throw new UsernameNotFoundException("Invalid access");
        //JwtResponse response = simpleAuthService.authenticate(jwtRequest);
        //return ResponseEntity.ok(response);
        return ResponseEntity.ok(simpleAuthService.authenticate(jwtRequest));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestParam("token") String token
    ) {
        simpleAuthService.validate(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        JwtResponse response = simpleAuthService.refresh(refreshRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<JwtResponse> logout() {
        JwtResponse response = new JwtResponse();
        return ResponseEntity.ok(response);
    }

}
