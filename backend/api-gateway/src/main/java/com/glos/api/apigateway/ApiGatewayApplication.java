package com.glos.api.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("feed-service", r -> r
                        .path("/api/feed", "/api/feed/**")
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri("http://localhost:9004")
                )
                .route("auth-service", r -> r
                        .path("/api/auth/**", "/api/auth")
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri("http://localhost:9006"))
                .build();
    }
}
