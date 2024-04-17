package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entity.AccessType;
import com.glos.databaseAPIService.domain.entity.User;
import jakarta.persistence.*;

import java.util.List;

public class GroupFilter
{
    private Long id;
    private User owner;

    private List<User> users;

    private List<AccessType> accessTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }
}
