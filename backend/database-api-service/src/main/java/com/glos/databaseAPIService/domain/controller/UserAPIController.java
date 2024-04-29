package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.filters.UserFilter;
import com.glos.databaseAPIService.domain.entityMappers.UserMapper;
import com.glos.databaseAPIService.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * 	@author - yablonovskydima
 */
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

    @GetMapping("/{id:\\d}")
    public ResponseEntity<User> getUser(@PathVariable Long id)
    {
        return ResponseEntity.of(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        userService.create(user);
       return ResponseEntity.created(URI.create("/v1/users/"+user.getId())).body(user);
    }

    @DeleteMapping("/{id:\\d}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id:\\d}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser)
    {
        userService.update(id, newUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username)
    {
        return ResponseEntity.of(userService.findByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.of(userService.findByEmail(email));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        return ResponseEntity.of(userService.findByPhoneNumber(phoneNumber));
    }

    @GetMapping
    public List<User> getUsersByFilter(@ModelAttribute User filter)
    {
        return userService.getAll(filter);
    }

}
