package com.glos.api.userservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.userservice.client.GroupAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.utils.MapUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{username}/groups")
    public List<Group> getUsersGroups(@PathVariable("username") String username)
    {
        return userAPIClient.getUserByUsername(username).getBody().getGroups();
    }

    @GetMapping("/{username}/groups/{groupName}")
    public ResponseEntity<List<Group>> getUsersGroupByName(@PathVariable("username") String username, @PathVariable("groupName") String groupName)
    {
        Group filter = new Group();
        filter.setName(groupName);
        filter.setOwner(userAPIClient.getUserByUsername(username).getBody());
        Map<String, Object> map = MapUtil.mapGroupFilter(filter);
        return ResponseEntity.ok(groupAPIClient.getGroupsByFilters(map).getBody());
    }
}
