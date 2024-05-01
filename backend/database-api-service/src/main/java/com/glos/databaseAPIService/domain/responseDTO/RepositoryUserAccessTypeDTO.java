package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.Repository;
import com.glos.api.entities.User;
import jakarta.persistence.*;

public class RepositoryUserAccessTypeDTO
{
    private Long id;

    private Repository repository;

    private User user;

    private AccessType accessType;

    public RepositoryUserAccessTypeDTO(Long id, Repository repository, User user, AccessType accessType) {
        this.id = id;
        this.repository = repository;
        this.user = user;
        this.accessType = accessType;
    }

    public RepositoryUserAccessTypeDTO() {
    }

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

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
