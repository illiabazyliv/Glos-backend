package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.databaseAPIService.domain.entities.AccessType;

public class FileUserAccessTypeDTO
{
    private Long id;

    private FileDTO file;

    private UserDTO user;

    private AccessType accessType;

    public FileUserAccessTypeDTO(Long id, FileDTO file, UserDTO user, AccessType accessType) {
        this.id = id;
        this.file = file;
        this.user = user;
        this.accessType = accessType;
    }

    public FileUserAccessTypeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
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
