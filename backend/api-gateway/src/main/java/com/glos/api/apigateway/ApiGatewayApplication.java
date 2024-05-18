package com.glos.api.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
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
                .route("user-manager", r -> r
                        .path("/api/users", "/api/users/**")
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri("http://localhost:9005")
                )
                .build();
    }

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder, GatewayFilter jwtAuthenticationFilter) {
//        return builder.routes()
//                .route("feed-service", r -> r
//                        .path("/api/feed", "/api/feed/**")
//                        .filters(f -> f.rewritePath("/api", "/api/v1"))
//                        .uri("lb://FEED-SERVICE")
//                )
//                .route("auth-service", r -> r
//                        .path(
//                                "/api/auth",
//                                "/api/auth/register",
//                                "/api/auth/login",
//                                "/api/auth/validate",
//                                "/api/refresh",
//                                "/api/logout"
//                        )
//                        .filters(f -> f.rewritePath("/api", "/api/v1"))
//                        .uri("lb://AUTH-SERVICE"))
//                .route("auth-service", r -> r
//                        .path(
//                                "/api/auth/admin/register"
//                        )
//                        .filters(f -> f.rewritePath("/api", "/api/v1"))
//                        .uri("lb://AUTH-SERVICE"))
//                .route("user-manager", r -> r
//                        .path(
//                                "/api/users",
//                                "/api/users/{id}",
//                                "/api/users/")
//                        .filters(f -> f.rewritePath("/api", "/api/v1"))
//                        .uri("lb://USER-MANAGER-SERVICE")
//                )
//                .route("user-manager", r -> r
//                        .path(
//                                "/api/users/{role}"
//                        )
//                        .filters(f -> f.rewritePath("/api", "/api/v1"))
//                        .uri("lb://USER-MANAGER-SERVICE")
//                ).build();
//    }
}
