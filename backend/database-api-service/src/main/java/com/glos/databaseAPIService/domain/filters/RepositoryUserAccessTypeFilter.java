package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entity.AccessType;
import com.glos.databaseAPIService.domain.entity.Repository;
import com.glos.databaseAPIService.domain.entity.User;
import jakarta.persistence.*;

import java.util.Map;

/**
 * 	@author - yablonovskydima
 */
public class RepositoryUserAccessTypeFilter implements EntityFilter
{
    private Long id;

    private Repository repository;

    private User user;

    private AccessType accessType;

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

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "id", id,
                "repository", repository,
                "user", user,
                "accessType", accessType
        );
    }
}
