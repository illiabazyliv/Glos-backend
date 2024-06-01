package com.glos.api.apigateway.cofing;

import com.glos.api.apigateway.cofing.security.JwtAuthenticationFilter;
import com.glos.api.apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public GatewayFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil).apply(new JwtAuthenticationFilter.Config());
    }

}
