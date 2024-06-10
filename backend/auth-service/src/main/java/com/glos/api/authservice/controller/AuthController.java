package com.glos.api.authservice.controller;

import com.glos.api.authservice.dto.ChangeRequest;
import com.glos.api.authservice.dto.SignInRequest;
import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.entities.Roles;
import com.glos.api.authservice.entities.User;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.util.security.*;
import com.glos.api.authservice.validation.OnCreate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
        JwtEntity jwtEntity = complateJwtEntity(request, Roles.ROLE_USER);
        JwtResponse response = simpleAuthService.register(jwtEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<JwtResponse> registerAdmin(
            @RequestBody @Validated(OnCreate.class) SignUpRequest request
    ) {
        JwtEntity jwtEntity = complateJwtEntity(request, Roles.ROLE_ADMIN);
        JwtResponse response = simpleAuthService.register(jwtEntity);
        return ResponseEntity.ok(response);
    }

    private JwtEntity complateJwtEntity(SignUpRequest request, Roles role) {
        return new JwtEntity(() -> {
            User user = signUpRequestMapper.toEntity(request);
            user.setRoles(Collections.singletonList(role.asEntity()));
            return user;
        });
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid SignInRequest signInRequest
    ) {
        return ResponseEntity.ok(simpleAuthService.authenticate(signInRequest));
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

    @PutMapping("/users/{username}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable("username") String username,
                                            @RequestBody ChangeRequest request)
    {
        if (request.getOLD().equals(request.getNEW())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old password match to new password");
        }
        simpleAuthService.changePassword(username, request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/users/{username}/change-phone-number")
    public ResponseEntity<?> changePhoneNumber(@PathVariable("username") String username,
                                            @RequestBody ChangeRequest request)
    {
        if (request.getOLD().equals(request.getNEW())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changePhoneNumber(username, request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/users/{username}/change-username")
    public ResponseEntity<?> changeUsername(@PathVariable("username") String username,
                                               @RequestBody ChangeRequest request)
    {
        if (request.getOLD().equals(request.getNEW())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changeUsername(username, request);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/users/{username}/change-email")
    public ResponseEntity<?> changeEmail(@PathVariable("username") String username,
                                            @RequestBody ChangeRequest request)
    {
        if (request.getOLD().equals(request.getNEW())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old value match to new value");
        }
        simpleAuthService.changeEmail(username, request);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/users/{username}/drop-account")
    public ResponseEntity<?> dropAccount(@PathVariable("username") String username)
    {
        simpleAuthService.deleteAccount(username);
        return ResponseEntity.accepted().build();
    }

}
