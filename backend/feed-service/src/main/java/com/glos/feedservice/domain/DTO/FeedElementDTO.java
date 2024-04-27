package com.glos.feedservice.domain.DTO;
/**
 * 	@author - yablonovskydima
 */
public class FeedElementDTO
{
    private RepositoryDTO repository;

    public FeedElementDTO(RepositoryDTO repository)
    {
        this.repository = repository;
    }

    public RepositoryDTO getRepository() {
        return repository;
    }

    public void setRepository(RepositoryDTO repository) {
        this.repository = repository;
    }

    public FeedElementDTO() {


    }
}
