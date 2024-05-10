package com.glos.groupservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.groupservice.client.GroupAPIClient;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.mappers.AutoMapper;
import com.glos.groupservice.responseDTO.GroupDTO;
import com.glos.groupservice.responseDTO.UserDTO;
import com.glos.groupservice.responseMappers.GroupDTOMapper;
import com.glos.groupservice.responseMappers.UserDTOMapper;
import com.glos.groupservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping
public class FriendsController
{
    private final GroupAPIClient groupAPIClient;
    private final UserDTOMapper userDTOMapper;
    private final GroupDTOMapper groupDTOMapper;
    private final AutoMapper<Group, GroupDTO> mapper;

    public FriendsController(GroupAPIClient groupAPIClient,
                             UserDTOMapper userDTOMapper,
                             GroupDTOMapper groupDTOMapper) {
        this.groupAPIClient = groupAPIClient;
        this.userDTOMapper = userDTOMapper;
        this.groupDTOMapper = groupDTOMapper;
        this.mapper = new AbstractMapper<Group, GroupDTO>() {
            @Override
            protected void postDtoCopy(Group source, GroupDTO destination) {
                super.postDtoCopy(source, destination);
            }
        };
    }

    @GetMapping("/users/{username}/friends")
    public ResponseEntity<GroupDTO> getFriends(@PathVariable("username") String username)
    {
        User user = new User();
        user.setUsername(username);
        Group filter = new Group();
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        Stream<Group> groups = groupAPIClient.getGroupsByFilters(map).getBody().stream();
        return ResponseEntity.ok(groups.map((x) -> {return transferEntityDTO(x, new GroupDTO());}).toList().getFirst());
    }

    @PutMapping("/users/{username}/friends/{friendUsername}")
    public ResponseEntity<GroupDTO> getFriendByUsername(@PathVariable("username") String username,
                                                       @PathVariable("friendUsername") String friendUsername,
                                                       @RequestBody User friend
                                                       )
    {
        User user = new User();
        user.setUsername(username);
        Group filter = new Group();
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        Group group = groupAPIClient.getGroupsByFilters(map).getBody().stream().findFirst().get();
        group.getUsers().add(user);
        return ResponseEntity.ok(transferEntityDTO(group, new GroupDTO()));
    }

    @DeleteMapping("/users/{username}/friends/{friendUsername}")
    public ResponseEntity<?> deleteFriend(@PathVariable("username") String username,
                                          @PathVariable("friendUsername") String friendUsername)
    {
        User user = new User();
        user.setUsername(username);
        Group filter = new Group();
        filter.setOwner(user);
        Map<String, Object> map = MapUtils.mapGroupFilter(filter);
        Group group = groupAPIClient.getGroupsByFilters(map).getBody().stream().findFirst().get();
        group.getUsers().removeIf((x) -> {return x.getUsername().equals(friendUsername);});
        groupAPIClient.updateGroup(group.getId(), group);
        return ResponseEntity.noContent().build();
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
