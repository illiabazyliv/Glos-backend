package com.glos.commentservice.entities;

import jakarta.persistence.AccessType;

public class RepositoryUserAccessType
{

    private Long id;

    private Repository repository;

    private User user;

    private jakarta.persistence.AccessType accessType;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public jakarta.persistence.AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
