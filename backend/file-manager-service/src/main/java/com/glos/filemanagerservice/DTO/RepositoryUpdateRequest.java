package com.glos.filemanagerservice.DTO;

import com.glos.filemanagerservice.entities.Repository;

import java.util.List;

public class RepositoryUpdateRequest
{
    public static class RepositoryNode
    {
        private Long id;
        private String repositoryBody;

        public RepositoryNode(Long id, String repositoryBody) {
            this.id = id;
            this.repositoryBody = repositoryBody;
        }

        public RepositoryNode() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRepositoryBody() {
            return repositoryBody;
        }

        public void setRepositoryBody(String repositoryBody) {
            this.repositoryBody = repositoryBody;
        }
    }
    private List<RepositoryNode> repositories;

    public RepositoryUpdateRequest(List<RepositoryNode> repositories) {
        this.repositories = repositories;
    }

    public List<RepositoryNode> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryNode> repositories) {
        this.repositories = repositories;
    }
}
