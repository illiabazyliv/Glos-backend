package com.glos.api.authservice.util.security;

import io.jsonwebtoken.Jwt;

public interface AuthService {

    JwtResponse authenticate(JwtRequest jwtRequest);
    JwtResponse refresh(String refreshToken);
    JwtResponse validate(String token);

}
