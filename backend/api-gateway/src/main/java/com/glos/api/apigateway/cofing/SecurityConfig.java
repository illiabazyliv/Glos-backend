package com.glos.api.apigateway.cofing;

import com.glos.api.apigateway.cofing.security.JwtAuthenticationFilter;
import com.glos.api.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public GatewayFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil).apply(new JwtAuthenticationFilter.Config());
    }

}
