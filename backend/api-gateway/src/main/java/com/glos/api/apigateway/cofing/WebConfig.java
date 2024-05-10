package com.glos.api.apigateway.cofing;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(org.springframework.web.reactive.config.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
        //.allowCredentials(true);
    }

}
