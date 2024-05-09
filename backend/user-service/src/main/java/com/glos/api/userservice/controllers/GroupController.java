package com.glos.api.userservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.api.userservice.client.GroupAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.exeptions.UserNotFoundException;
import com.glos.api.userservice.utils.MapUtil;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping
public class GroupController
{
    private final GroupAPIClient groupAPIClient;
    private final UserAPIClient userAPIClient;


    public GroupController(GroupAPIClient groupAPIClient, UserAPIClient userAPIClient) {
        this.groupAPIClient = groupAPIClient;
        this.userAPIClient = userAPIClient;
    }

    //crud операції яких немає в api карті
    @GetMapping("/groups/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id)
    {
        return groupAPIClient.getGroupById(id);
    }


    @GetMapping("/groups/all")
    public ResponseEntity<List<Group>> getAllGroups()
    {
        return groupAPIClient.getAllGroups();
    }

    @GetMapping("/groups")
    public ResponseEntity<Stream<Group>> getGroupsByFilters(@SpringQueryMap Map<String, Object> filter)
    {
        return groupAPIClient.getGroupsByFilters(filter);
    }

    //ендпоінти які є в api карті
    @GetMapping("/{username}/groups")
    public ResponseEntity<List<Group>> getUsersGroups(@PathVariable("username") String username)
    {
        return ResponseEntity.ok(userAPIClient.getUserByUsername(username).getBody().getGroups());
    }

    @GetMapping("/{username}/groups/{groupName}")
    public ResponseEntity<Group> getUsersGroupByName(@PathVariable("username") String username,
                                                           @PathVariable("groupName") String groupName)
    {
        Group filter = new Group();
        filter.setName(groupName);
        filter.setOwner(userAPIClient.getUserByUsername(username).getBody());

        Map<String, Object> map = MapUtil.mapGroupFilter(filter);
        return ResponseEntity.of(groupAPIClient.getGroupsByFilters(map).getBody().findFirst());
    }

    @PutMapping("/{username}/group/{groupName}")
    public ResponseEntity<Group> createGroup(@PathVariable("username") String username,
                                             @PathVariable("groupName") String groupName,
                                             @RequestBody Group group)
    {
        group.setName(groupName);
        User owner = userAPIClient.getUserByUsername(username).getBody();
        Group created = groupAPIClient.createGroup(group).getBody();
        owner.addGroup(created);
        userAPIClient.updateUser(owner.getId(), owner);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{username}/groups/{groupName}")
    public ResponseEntity<?> deleteGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName)
    {
        User owner = userAPIClient.getUserByUsername(username).getBody();
        Group group = owner.getGroups().stream().filter((x) -> {return x.getName().equals(groupName);}).findFirst().orElseThrow();
        return groupAPIClient.deleteGroup(group.getId());
    }

    @PatchMapping("/{username}/groups/{groupName}")
    public ResponseEntity<?> updateGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName,
                                         @RequestBody Group newGroup)
    {
        User owner = userAPIClient.getUserByUsername(username).getBody();
        Group group = owner.getGroups().stream().filter((x) -> {return x.getName().equals(groupName);}).findFirst().orElseThrow();
        return groupAPIClient.updateGroup(group.getId(), newGroup);
    }
}
