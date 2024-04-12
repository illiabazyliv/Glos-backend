package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.User;
import com.glos.databaseAPIService.domain.entity.UserFilter;
import com.glos.databaseAPIService.domain.mappers.UserMapper;
import com.glos.databaseAPIService.domain.repository.UserRepository;
import com.glos.databaseAPIService.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public HttpStatus deleteUser(@PathVariable Long id)
    {
        userService.delete(userService.findById(id).get());
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Long id)
    {
        User user = userService.findById(id).get();
        userMapper.transferEntityDto(newUser, user);
        userService.save(user);
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
