package com.glos.feedservice.domain.DTO;

public class FeedElementDTO
{
    private RepositoryDTO repositoryDTO;

    public FeedElementDTO(RepositoryDTO repositoryDTO)
    {
        this.repositoryDTO = repositoryDTO;
    }

    public RepositoryDTO getRepositoryDTO() {
        return repositoryDTO;
    }

    public void setRepositoryDTO(RepositoryDTO repositoryDTO) {
        this.repositoryDTO = repositoryDTO;
    }

}
