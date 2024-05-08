package com.glos.api.userservice.client;

import com.glos.api.entities.Group;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@FeignClient(name = "group")
public interface GroupAPIClient
{
    @GetMapping("/{id}")
    ResponseEntity<Group> getGroupById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Group> createGroup(@RequestBody Group group);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteGroup(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody Group newGroup);

    @GetMapping()
    public ResponseEntity<List<Group>> getAllGroups();

    @GetMapping("/filter")
    ResponseEntity<List<Group>> getGroupsByFilters(@SpringQueryMap Map<String, Object> filter);
}
