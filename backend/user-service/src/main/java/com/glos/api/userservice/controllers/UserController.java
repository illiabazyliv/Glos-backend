package com.glos.api.userservice.controllers;

import com.glos.api.entities.User;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserAPIClient userAPIClient;


    @Autowired
    public UserController(UserAPIClient userAPIClient) {
        this.userAPIClient = userAPIClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id)
    {
        return userAPIClient.getById(id);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user)
    {
        return userAPIClient.create(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        return userAPIClient.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser)
    {

        return userAPIClient.updateUser(id, newUser);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username)
    {
        return userAPIClient.getUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        return userAPIClient.getUserByEmail(email);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        ResponseEntity<User> user = userAPIClient.getUserByPhoneNumber(phoneNumber);
        System.out.println(user.getStatusCode().is2xxSuccessful());
        return user;
    }

    @GetMapping
    public List<User> getAllByFilter(@ModelAttribute User filter)
    {
        Map<String, Object> map = MapUtil.mapUserFilter(filter);
        return userAPIClient.getUsersByFilter(map);
    }
}
