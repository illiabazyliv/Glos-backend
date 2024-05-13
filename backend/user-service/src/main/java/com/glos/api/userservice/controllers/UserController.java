package com.glos.api.userservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.api.userservice.client.GroupAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.facade.GroupAPIFacade;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.responseMappers.UserDTOMapper;
import com.glos.api.userservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserAPIClient userAPIClient;
    private final GroupAPIFacade groupAPIFacade;
    private final GroupAPIClient groupAPIClient;
    private final UserDTOMapper userDTOMapper;

    public UserController(UserAPIClient userAPIClient, GroupAPIFacade groupAPIFacade, GroupAPIClient groupAPIClient, UserDTOMapper userDTOMapper) {
        this.userAPIClient = userAPIClient;
        this.groupAPIFacade = groupAPIFacade;
        this.groupAPIClient = groupAPIClient;
        this.userDTOMapper = userDTOMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIClient.getById(id).getBody()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user)
    {

        User mapped = userDTOMapper.toEntity(user);
        ResponseEntity<User> userResponseEntity = userAPIClient.create(mapped);

        if (userResponseEntity.getStatusCode().is2xxSuccessful())
        {
            Group friends = new Group();
            friends.setName("friends");
            User owner = new User();
            owner.setId(userResponseEntity.getBody().getId());
            friends.setOwner(owner);
            groupAPIFacade.putGroup(friends, owner.getUsername(), "friends");
        }
        return ResponseEntity.ok(userDTOMapper.toDto(userResponseEntity.getBody()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        userAPIClient.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser)
    {
        userAPIClient.updateUser(id, newUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIClient.getUserByUsername(username).getBody()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIClient.getUserByEmail(email).getBody()));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber)
    {
        return ResponseEntity.ok(userDTOMapper.toDto(userAPIClient.getUserByPhoneNumber(phoneNumber).getBody()));
    }

    @GetMapping
    public List<User> getAllByFilter(@ModelAttribute User filter)
    {
        Map<String, Object> map = MapUtils.map(filter);
        return userAPIClient.getUsersByFilter(map);
    }

    @PutMapping("/{username}/block")
    public ResponseEntity<?> blockUser(@PathVariable("username") String username)
    {
        User user = userAPIClient.getUserByUsername(username).getBody();
        user.setIs_account_non_locked(Boolean.FALSE);
        return userAPIClient.updateUser(user.getId(), user);
    }

    @PutMapping("/{username}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable("username") String username)
    {
        User user = userAPIClient.getUserByUsername(username).getBody();
        user.setIs_account_non_locked(Boolean.TRUE);
        return userAPIClient.updateUser(user.getId(), user);
    }

    @PutMapping("/{username}/enable")
    public ResponseEntity<?> enableUser(@PathVariable("username") String username)
    {
        User user = userAPIClient.getUserByUsername(username).getBody();
        user.setIs_enabled(Boolean.TRUE);
        return userAPIClient.updateUser(user.getId(), user);
    }

    @PutMapping("/{username}/disable")
    public ResponseEntity<?> disableUser(@PathVariable("username") String username)
    {
        User user = userAPIClient.getUserByUsername(username).getBody();
        user.setIs_enabled(Boolean.FALSE);
        return userAPIClient.updateUser(user.getId(), user);
    }
}
