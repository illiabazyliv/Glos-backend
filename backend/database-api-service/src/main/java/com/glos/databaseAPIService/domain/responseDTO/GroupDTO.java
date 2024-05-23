package com.glos.databaseAPIService.domain.responseDTO;

import jakarta.persistence.*;

import java.util.List;

public class GroupDTO
{
    private Long id;

    private String name;

    private UserDTO owner;

    private List<AccessType> accessTypes;
    private List<UserDTO> users;


    public GroupDTO() {
    }

    public GroupDTO(Long id, String name, UserDTO owner, List<AccessType> accessTypes, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.accessTypes = accessTypes;
        this.users = users;
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

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
