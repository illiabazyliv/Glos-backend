package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAPIController
{
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserAPIController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id)
    {
        return ResponseEntity.of(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        userService.save(user);
       return ResponseEntity.created(URI.create("/v1/users/"+user.getId())).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.delete(userService.findById(id).get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id)
    {
        userService.update(newUser, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username)
    {
        return ResponseEntity.of(userService.findByUsername(username));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.of(userService.findByEmail(email));
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        return ResponseEntity.of(userService.findByPhoneNumber(phoneNumber));
    }

    @GetMapping("/all")
    public List<User> getUsers(@ModelAttribute UserFilter userFilter)
    {
        return userService.getAll(userFilter);
    }



}
