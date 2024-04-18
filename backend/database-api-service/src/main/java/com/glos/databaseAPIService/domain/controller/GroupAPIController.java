package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.filters.GroupFilter;
import com.glos.databaseAPIService.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * 	@author - yablonovskydima
 */

@RestController
@RequestMapping("/groups")
public class GroupAPIController
{
    private final GroupService groupService;

    @Autowired
    public GroupAPIController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id)
    {
        return ResponseEntity.of(groupService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody Group group)
    {
        groupService.create(group);
        return ResponseEntity.created(URI.create("/v1/groups/"+group.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id)
    {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestBody Group newGroup)
    {
        groupService.update(id, newGroup);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public List<Group> getAllGroups()
    {
        return groupService.getAll();
    }

    @GetMapping("/filter")
    public List<Group> getGroupsByFilters(@ModelAttribute GroupFilter filter)
    {
        return groupService.getAll(filter);
    }
}
