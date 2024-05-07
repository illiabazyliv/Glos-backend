package com.glos.api.userservice.controllers;

import com.glos.api.entities.User;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<User> getAllByFilter(@ModelAttribute User filter)
    {
        Map<String, Object> map = MapUtil.mapUserFilter(filter);
        return userAPIClient.getUsersByFilter(map);
    }
}
