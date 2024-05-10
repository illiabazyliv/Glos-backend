package com.glos.api.userservice.client;

import com.glos.api.entities.Group;
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
}
