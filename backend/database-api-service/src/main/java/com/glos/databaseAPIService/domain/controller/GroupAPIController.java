package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.GroupFilter;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.GroupDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.GroupService;
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
@RequestMapping("/groups")
public class GroupAPIController
{
    private final GroupService groupService;
    private final GroupDTOMapper groupDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public GroupAPIController(GroupService groupService, GroupDTOMapper groupDTOMapper, UserDTOMapper userDTOMapper) {
        this.groupService = groupService;
        this.groupDTOMapper = groupDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id)
    {
        Group g = groupService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("Group is not found");} );
        GroupDTO groupDTO = new GroupDTO();
        groupDTO = transferEntityDTO(g, groupDTO);
        return ResponseEntity.of(Optional.of(groupDTO));
    }

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody Group group, UriComponentsBuilder uriBuilder)
    {
        Group g = groupService.create(group);
        GroupDTO groupDTO = new GroupDTO();
        groupDTO = transferEntityDTO(group, groupDTO);
        return ResponseEntity.created(
                uriBuilder.path("/groups/{id}")
                        .build(g.getId())).body(groupDTO);
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
    public ResponseEntity<List<GroupDTO>> getAllGroups()
    {
        List<Group> groups = groupService.getAll();
        return ResponseEntity.ok(groups.stream().map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GroupDTO>> getGroupsByFilters(@ModelAttribute Group filter)
    {
        List<Group> groups = groupService.getAll(filter);
        return ResponseEntity.ok(groups.stream().map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList());
    }

    GroupDTO transferEntityDTO(Group source, GroupDTO destination)
    {
        UserDTO userDTO = new UserDTO();
        userDTOMapper.transferEntityDto(source.getOwner(), userDTO);

        List<UserDTO> users = source.getUsers().stream().map((x) -> {UserDTO newUser = new UserDTO();
            userDTOMapper.transferEntityDto(x, newUser);
            return newUser;}).toList();

        groupDTOMapper.transferEntityDto(source, destination);
        destination.setOwner(userDTO);
        destination.setUsers(users);
        return destination;
    }
}
