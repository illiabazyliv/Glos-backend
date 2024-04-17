package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entity.AccessType;
import com.glos.databaseAPIService.domain.entity.File;
import com.glos.databaseAPIService.domain.entity.User;

public class FileUserAccessTypeFilter
{


    private Long id;

    private File file;

    private User user;

    private AccessType accessType;

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
