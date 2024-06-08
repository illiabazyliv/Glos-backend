package com.glos.api.authservice.client;

import com.glos.api.authservice.dto.Page;
import com.glos.api.authservice.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user")
public interface UserAPIClient {

    @PostMapping("/users/{role}")
    ResponseEntity<User> create(@RequestBody User user, @PathVariable String role);

    @GetMapping("/users/{id}")
    ResponseEntity<User> getById(@PathVariable Long id);

    @GetMapping("/users/username/{username}")
    ResponseEntity<User> getByUsername(@PathVariable String username);

    @GetMapping("/users")
    ResponseEntity<Page<User>> getAll(@SpringQueryMap Map<String, Object> params);

}
