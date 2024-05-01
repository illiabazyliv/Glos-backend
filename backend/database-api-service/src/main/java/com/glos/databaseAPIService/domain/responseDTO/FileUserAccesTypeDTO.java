package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.File;
import com.glos.api.entities.User;
import jakarta.persistence.*;

public class FileUserAccesTypeDTO
{
    private Long id;

    private File file;

    private User user;

    private AccessType accessType;

    public FileUserAccesTypeDTO(Long id, File file, User user, AccessType accessType) {
        this.id = id;
        this.file = file;
        this.user = user;
        this.accessType = accessType;
    }

    public FileUserAccesTypeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
