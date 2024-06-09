package com.glos.api.authservice.client;

import com.glos.api.authservice.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-database")
public interface UserDatabaseAPIClient {

    @GetMapping("/users/{id}")
    ResponseEntity<User> getById(@PathVariable Long id);

    @GetMapping("/users/username/{username}")
    ResponseEntity<User> getByUsername(@PathVariable String username);

}
