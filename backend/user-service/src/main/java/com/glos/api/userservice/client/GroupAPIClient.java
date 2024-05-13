package com.glos.api.userservice.client;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "group")
public interface GroupAPIClient
{
    @PutMapping("/users/{username}/groups/{groupName}")
    ResponseEntity<Group> createGroup(@PathVariable("username") String username,
                                      @PathVariable("groupName") String groupName,
                                      @RequestBody Group group);

    @DeleteMapping("/users/{username}/groups/{groupName}")
    ResponseEntity<?> deleteGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName);

    @GetMapping("/users/groups")
    ResponseEntity<List<Group>> getGroupsByFilters(@SpringQueryMap Map<String, Object> filter);

    @PatchMapping("/users/{username}/groups/{groupName}")
    ResponseEntity<?> updateGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName,
                                         @RequestBody Group newGroup);
}
