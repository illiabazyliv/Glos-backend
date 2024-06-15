package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.*;

import java.util.List;

public class RepositoryRequest {

    private String rootPath;
    private String rootName;
    private User owner;
    private String description;
    private List<AccessModel> accessTypes;
    private List<String> tags;

    public RepositoryRequest() {

    }

    public RepositoryRequest(String rootPath, String rootName, User owner, String description, List<AccessModel> accessTypes, List<String> tags) {
        this.rootPath = rootPath;
        this.rootName = rootName;
        this.owner = owner;
        this.description = description;
        this.accessTypes = accessTypes;
        this.tags = tags;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AccessModel> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessModel> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
