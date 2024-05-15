package com.glos.api.authservice.client;

import com.glos.api.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
@FeignClient(name = "user-database")
public interface UserDatabaseAPIClient {

    @PostMapping("/users")
    ResponseEntity<User> create(@RequestBody User user);

    @GetMapping("/users/{id}")
    ResponseEntity<User> getById(@PathVariable Long id);

    @GetMapping("/users/username/{username}")
    Optional<User> getByUsername(@PathVariable String username);

    @GetMapping("/users")
    ResponseEntity<List<User>> getAll();


}
