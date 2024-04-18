package com.glos.databaseAPIService.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.glos.api.entities")
public class JpaConfiguration {

}