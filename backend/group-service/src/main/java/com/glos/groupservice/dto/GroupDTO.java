package com.glos.groupservice.dto;

import com.glos.api.entities.AccessType;

import java.util.List;

public class GroupDTO
{
    private Long id;
    private String name;
    private UserDTO owner;
    private List<UserDTO> users;
    private List<AccessType> accessTypes;

    public GroupDTO(Long id, String name, UserDTO owner, List<UserDTO> users, List<AccessType> accessTypes) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
        this.accessTypes = accessTypes;
    }

    public GroupDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }
}
