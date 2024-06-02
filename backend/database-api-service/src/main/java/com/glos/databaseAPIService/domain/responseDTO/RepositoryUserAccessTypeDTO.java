package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.databaseAPIService.domain.entities.AccessType;

public class RepositoryUserAccessTypeDTO
{
    private Long id;

    private RepositoryDTO repository;

    private UserDTO user;

    private AccessType accessType;

    public RepositoryUserAccessTypeDTO(Long id, RepositoryDTO repository, UserDTO user, AccessType accessType) {
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

    public RepositoryDTO getRepository() {
        return repository;
    }

    public void setRepository(RepositoryDTO repository) {
        this.repository = repository;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
