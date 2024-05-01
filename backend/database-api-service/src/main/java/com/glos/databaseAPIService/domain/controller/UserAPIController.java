package com.glos.databaseAPIService.domain.controller;

import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/users")
public class UserAPIController
{
    private final UserService userService;
    private final UserDTOMapper mapper;

    @Autowired
    public UserAPIController(UserService userService, UserDTOMapper mapper)
    {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id)
    {
        UserDTO userDTO = new UserDTO();
        User user = userService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user, UriComponentsBuilder uriBuilder)
    {
        User u = userService.create(user);
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.created(
                uriBuilder.path("/repositories/{id}")
                        .build(userDTO.getId())).body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id)
    {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser)
    {
        userService.update(id, newUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username)
    {
        User user = userService.findByUsername(username).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)
    {
        User user = userService.findByEmail(email).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        User user = userService.findByPhoneNumber(phoneNumber).orElseThrow(() -> {return new ResourceNotFoundException("User is not found");} );
        UserDTO userDTO = new UserDTO();
        mapper.transferEntityDto(user, userDTO);
        return ResponseEntity.of(Optional.of(userDTO));
    }

    @GetMapping
    public List<UserDTO> getUsersByFilter(@ModelAttribute User filter)
    {
        List<User> users = userService.getAll(filter);
        List<UserDTO> userDTOS = new ArrayList<>();

//        for (User u:users)
//        {
//            UserDTO userDTO = new UserDTO();
//            mapper.transferEntityDto(u, userDTO);
//            userDTOS.add(userDTO);
//        }

        List<UserDTO> u = users.stream().map(mapper::toDto).toList();

        return userDTOS;
    }

    //GET users not working
}
