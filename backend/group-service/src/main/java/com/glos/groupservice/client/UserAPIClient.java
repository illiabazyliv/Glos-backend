package com.glos.groupservice.client;

import com.glos.api.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user")
public interface UserAPIClient
{
    @GetMapping("/{id}")
    ResponseEntity<User> getById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<User> create(@RequestBody User user);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser);

    @GetMapping("/username/{username}")
    ResponseEntity<User> getUserByUsername(@PathVariable String username);

    @GetMapping("/email/{email}")
    ResponseEntity<User> getUserByEmail(@PathVariable String email);

    @GetMapping("/phone-number/{phoneNumber}")
    ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber);

    @GetMapping
    List<User> getUsersByFilter(@SpringQueryMap Map<String, Object> filter);


}
