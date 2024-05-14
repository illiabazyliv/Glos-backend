package com.glos.api.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.glos.api.entities.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "user")
public interface UserAPIClient {

    @PostMapping
    ResponseEntity<User> create(@RequestBody User user);

    @GetMapping("/{id}")
    ResponseEntity<User> getById(@PathVariable Long id);

    @GetMapping("/username/{username}")
    ResponseEntity<User> getByUsername(@PathVariable String username);

}
