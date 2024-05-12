package com.glos.groupservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.User;
import com.glos.groupservice.client.GroupAPIClient;
import com.glos.groupservice.exeptions.UserNotFoundException;
import com.glos.groupservice.facade.GroupAPIFacade;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.mappers.AutoMapper;
import com.glos.groupservice.dto.GroupDTO;
import com.glos.groupservice.dto.UserDTO;
import com.glos.groupservice.responseMappers.GroupDTOMapper;
import com.glos.groupservice.responseMappers.UserDTOMapper;
import com.glos.groupservice.utils.Constants;
import com.glos.groupservice.utils.MapUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping
public class FriendsController
{
    private final GroupDTOMapper groupDTOMapper;
    private final GroupAPIFacade groupAPIFacade;

    public FriendsController(
            GroupDTOMapper groupDTOMapper,
            GroupAPIFacade groupAPIFacade
    ) {
        this.groupDTOMapper = groupDTOMapper;
        this.groupAPIFacade = groupAPIFacade;
    }

    @GetMapping("/users/{username}/friends")
    public ResponseEntity<GroupDTO> getFriends(@PathVariable("username") String username)
    {
        return ResponseEntity.of(
                groupAPIFacade.getByOwnerAndName(username, Constants.FRIENDS_GROUP_NAME)
                        .map(groupDTOMapper::toDto)
        );
    }

    @PutMapping("/users/{username}/friends/add-user/{friendUsername}")
    public ResponseEntity<GroupDTO> appendUser(@PathVariable("username") String username,
                                                       @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.appendUser(username, Constants.FRIENDS_GROUP_NAME, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @DeleteMapping("/users/{username}/friends/delete-user/{friendUsername}")
    public ResponseEntity<GroupDTO> deleteUser(@PathVariable("username") String username,
                                          @PathVariable("friendUsername") String friendUsername)
    {
        ResponseEntity<Group> response = groupAPIFacade.removeUser(username, Constants.FRIENDS_GROUP_NAME, friendUsername);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(groupDTOMapper.toDto(response.getBody()));
        }
        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
