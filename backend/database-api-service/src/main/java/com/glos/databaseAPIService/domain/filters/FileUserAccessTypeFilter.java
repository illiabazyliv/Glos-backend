package com.glos.databaseAPIService.domain.filters;



import com.glos.api.entities.AccessType;
import com.glos.api.entities.File;
import com.glos.api.entities.User;

import java.util.Map;

/**
 * @author Mykola Melnyk
 */
public class FileUserAccessTypeFilter implements EntityFilter
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

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "id", id,
                "file", file,
                "user", user,
                "accessType", accessType
        );
    }
}
