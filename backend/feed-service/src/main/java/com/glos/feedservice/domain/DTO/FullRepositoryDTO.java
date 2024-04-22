package com.glos.feedservice.domain.DTO;

import com.glos.feedservice.domain.entities.*;

import java.util.List;

public class FullRepositoryDTO
{
    private Long id;

    private String rootPath;

    private String rootName;

    private String rootFullName;

    private User owner;
    private String displayPath;

    private String displayName;

    private String displayFullName;

    private String description;

    private List<AccessType> accessTypes;

    private List<Tag> tags;

    private List<Comment> comments;
    private List<File> files;

    public FullRepositoryDTO(Long id,
                             String rootPath,
                             String rootName,
                             String rootFullName,
                             User owner,
                             String displayPath,
                             String displayName,
                             String displayFullName,
                             String description,
                             List<AccessType> accessTypes,
                             List<Tag> tags,
                             List<Comment> comments,
                             List<File> files)
    {
        this.id = id;
        this.rootPath = rootPath;
        this.rootName = rootName;
        this.rootFullName = rootFullName;
        this.owner = owner;
        this.displayPath = displayPath;
        this.displayName = displayName;
        this.displayFullName = displayFullName;
        this.description = description;
        this.accessTypes = accessTypes;
        this.tags = tags;
        this.comments = comments;
        this.files = files;
    }


    public FullRepositoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRootFullName() {
        return rootFullName;
    }

    public void setRootFullName(String rootFullName) {
        this.rootFullName = rootFullName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayFullName() {
        return displayFullName;
    }

    public void setDisplayFullName(String displayFullName) {
        this.displayFullName = displayFullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
