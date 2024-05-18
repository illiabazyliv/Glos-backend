package com.glos.api.authservice.util.security;

import io.jsonwebtoken.Jwt;

public interface AuthService {

    JwtResponse register(JwtEntity jwtEntity);
    JwtResponse authenticate(JwtRequest jwtRequest);
    JwtResponse refresh(JwtRefreshRequest refreshRequest);
    boolean validate(String token);

}
