package com.glos.api.apigateway.cofing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RouteConfig {

    @Autowired
    private Environment env;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("feed-service", r -> r
                        .path("/api/feed", "/api/feed/**")
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri(env.getProperty("FEED_SERVICE_URL","http://localhost:9004"))
                )
                .route("auth-service", r -> r
                        .path(
                                "/api/auth",
                                "/api/auth/**"
                        )
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri(env.getProperty("AUTH_SERVICE_URL", "http://localhost:9006"))
                )
                .route("user-manager", r -> r
                        .path(
                                "/api/users",
                                "/api/users/**"
                        )
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri(env.getProperty("USER_MANAGER_SERVICE_URL", "http://localhost:9005"))
                )
                .route("file-manager-service", r -> r
                        .path(
                                "/api/files",
                                "/api/repositories",
                                "/api/files/**",
                                "/api/repositories/**"
                        )
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri(env.getProperty("FILE_MANAGER_SERVICE_URL", "http://localhost:9010"))
                )
                .route("access-service", r -> r
                        .path(
                                "/api/access-types",
                                "/api/access-types/**"
                        )
                        .filters(f -> f.rewritePath("/api", "/api/v1"))
                        .uri(env.getProperty("ACCESS_SERVICE_URL", "http://localhost:9009"))
                )
                .build();
    }

}
