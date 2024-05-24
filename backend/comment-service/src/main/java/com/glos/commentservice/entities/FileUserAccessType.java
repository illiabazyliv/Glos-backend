package com.glos.commentservice.entities;

import jakarta.persistence.AccessType;


public class FileUserAccessType
{

    private Long id;

    private File file;

    private User user;

    private jakarta.persistence.AccessType accessType;

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

    public jakarta.persistence.AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
