package com.glos.api.userservice.controllers;

import com.glos.api.entities.Group;
import com.glos.api.entities.Role;
import com.glos.api.entities.User;
import com.glos.api.userservice.client.RoleAPIClient;
import com.glos.api.userservice.client.UserAPIClient;
import com.glos.api.userservice.responseDTO.Page;
import com.glos.api.userservice.responseDTO.RoleDTO;
import com.glos.api.userservice.responseDTO.UserDTO;
import com.glos.api.userservice.responseMappers.UserDTOMapper;
import com.glos.api.userservice.utils.MapUtils;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/roles")
public class UserRolesController
{
    private final RoleAPIClient roleAPIClient;

    public UserRolesController(RoleAPIClient roleAPIClient) {
        this.roleAPIClient = roleAPIClient;
    }

    @GetMapping("/{name}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable("name") String name)
    {
        Role role = roleAPIClient.getRoleByName(name).getBody();
        return ResponseEntity.ok(new RoleDTO(role.getName()));
    }

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {
        Map<String, Object> filter = new HashMap<>();
        filter.put("page", page);
        filter.put("size", size);
        filter.put("sort", sort);
        Page<Role> rolePage = roleAPIClient.getPage(filter).getBody();
        Page<RoleDTO> roleDTOPage = rolePage.map(role -> new RoleDTO(role.getName()));
        roleDTOPage.setNumber(page);
        roleDTOPage.setSize(size);
        String[] arr = sort.split(",");
        roleDTOPage.setSortBy(arr[0]);
        roleDTOPage.setSortDir(arr[1]);
        roleDTOPage.setSorted(true);
        return ResponseEntity.ok(roleDTOPage);

    }
 }
