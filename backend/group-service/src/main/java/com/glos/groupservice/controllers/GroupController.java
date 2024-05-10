package com.glos.groupservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.groupservice.client.GroupAPIClient;
import com.glos.groupservice.responseDTO.GroupDTO;
import com.glos.groupservice.responseDTO.UserDTO;
import com.glos.groupservice.responseMappers.GroupDTOMapper;
import com.glos.groupservice.responseMappers.UserDTOMapper;
import com.glos.groupservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping
public class GroupController
{
    private final GroupAPIClient groupAPIClient;
    private final UserDTOMapper userDTOMapper;
    private final GroupDTOMapper groupDTOMapper;


    public GroupController(GroupAPIClient groupAPIClient,
                           UserDTOMapper userDTOMapper,
                           GroupDTOMapper groupDTOMapper) {
        this.groupAPIClient = groupAPIClient;
        this.userDTOMapper = userDTOMapper;
        this.groupDTOMapper = groupDTOMapper;
    }
    @GetMapping("/users/groups/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id)
    {
        Group group = groupAPIClient.getGroupById(id).getBody();
        GroupDTO groupDTO = new GroupDTO();
        groupDTO = transferEntityDTO(group, groupDTO);
        return ResponseEntity.ok(groupDTO);
    }


    @GetMapping("/users/groups/all")
    public ResponseEntity<List<GroupDTO>> getAllGroups()
    {
        List<Group> groups = groupAPIClient.getAllGroups().getBody();
        List<GroupDTO> groupDTOS = groups.stream().map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList();
        return ResponseEntity.ok(groupDTOS);
    }

    @GetMapping("/users/groups")
    public ResponseEntity<List<GroupDTO>> getGroupsByFilters(@ModelAttribute Group filter)
    {
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        ResponseEntity<List<Group>> groups = groupAPIClient.getGroupsByFilters(map);
        List<GroupDTO> groupDTOS = groups.getBody().stream().map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList();
        return ResponseEntity.ok(groupDTOS);
    }

    @GetMapping("/users/{username}/groups")
    public ResponseEntity<List<GroupDTO>> getUsersGroups(@PathVariable("username") String username)
    {
        User user = new User();
        user.setUsername(username);
        Group filter = new Group();
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        List<Group> groups = groupAPIClient.getGroupsByFilters(map).getBody();
        List<GroupDTO> groupDTOS = groups.stream().map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList();
        return ResponseEntity.ok(groupDTOS);
    }

    @GetMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<GroupDTO> getUsersGroupByName(@PathVariable("username") String username,
                                                     @PathVariable("groupName") String groupName)
    {
        Group filter = new Group();
        filter.setName(groupName);
        User user = new User();
        user.setUsername(username);
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);

        Optional<GroupDTO> groupOpt = groupAPIClient.getGroupsByFilters(map).getBody().stream()
                .map(x -> transferEntityDTO(x, new GroupDTO()))
                .findFirst();

        return ResponseEntity.of(groupOpt);
    }

    @PutMapping("/users/{username}/group/{groupName}")
    public ResponseEntity<GroupDTO> createGroup(@PathVariable("username") String username,
                                             @PathVariable("groupName") String groupName,
                                             @RequestBody Group group)
    {
        if (group.getName() == null)
        {
            group.setName(groupName);
        }
        User user = new User();
        user.setUsername(username);
        user.addGroup(group);
        Group created = groupAPIClient.createGroup(group).getBody();
        return ResponseEntity.ok(transferEntityDTO(created, new GroupDTO()));
    }

    @DeleteMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<?> deleteGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName)
    {
        Group filter = new Group();
        filter.setName(groupName);
        User user = new User();
        user.setUsername(username);
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        Group group = groupAPIClient.getGroupsByFilters(map).getBody().get(0);
        return groupAPIClient.deleteGroup(group.getId());
    }

    @PatchMapping("/users/{username}/groups/{groupName}")
    public ResponseEntity<?> updateGroup(@PathVariable("username") String username,
                                         @PathVariable("groupName") String groupName,
                                         @RequestBody Group newGroup)
    {


        Group filter = new Group();
        filter.setName(groupName);
        User user = new User();
        user.setUsername(username);
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        Group group = groupAPIClient.getGroupsByFilters(map).getBody().get(0);
        return groupAPIClient.updateGroup(group.getId(), newGroup);
    }

    GroupDTO transferEntityDTO(Group source, GroupDTO destination)
    {
        UserDTO userDTO = new UserDTO();
        userDTOMapper.transferEntityDto(source.getOwner(), userDTO);
        groupDTOMapper.transferEntityDto(source, destination);
        destination.setOwner(userDTO);
        return destination;
    }
}
