package com.glos.api.userservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.api.userservice.client.GroupAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserAPIClient userAPIClient;
    private final GroupAPIClient groupAPIClient;


    @Autowired
    public UserController(UserAPIClient userAPIClient, GroupAPIClient groupAPIClient) {
        this.userAPIClient = userAPIClient;
        this.groupAPIClient = groupAPIClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id)
    {
        return userAPIClient.getById(id);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user)
    {

        ResponseEntity<User> userResponseEntity = userAPIClient.create(user);

        if (userResponseEntity.getStatusCode().is2xxSuccessful())
        {
            Group friends = new Group();
            friends.setName("friends");
            User owner = new User();
            owner.setId(userResponseEntity.getBody().getId());
            friends.setOwner(owner);
            ResponseEntity<Group> groupResponseEntity = groupAPIClient.createGroup(userResponseEntity.getBody().getUsername(), "friends", friends);
        }
        return userResponseEntity;
    }

    @PutMapping("/{username}/groups/{groupName}")
    public ResponseEntity<?> createGroup(@PathVariable("username") String username,
                                                @PathVariable("groupName") String groupName,
                                                @RequestBody Group group)
    {

        return groupAPIClient.createGroup(username, groupName, group);
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
        return user;
    }

    @GetMapping
    public List<User> getAllByFilter(@ModelAttribute User filter)
    {
        Map<String, Object> map = MapUtil.mapUserFilter(filter);
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
