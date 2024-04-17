package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.Repository;
import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryMapper;
import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
import com.glos.databaseAPIService.domain.repository.RepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class RepositoryService
{
    private final RepositoryRepository repository;
    private final RepositoryMapper repositoryMapper;

    @Autowired
    public RepositoryService(RepositoryRepository repository, RepositoryMapper repositoryMapper) {
        this.repository = repository;
        this.repositoryMapper = repositoryMapper;
    }

    public Optional<Repository> findById(Long id)
    {
        return repository.findById(id);
    }

    public Repository save(Repository repository)
    {
        return this.repository.save(repository);
    }

    public void delete(Repository repository)
    {
        this.repository.delete(repository);
    }

    public Repository update(Repository newRepository, Long id)
    {
        Repository repository = getRepositoryByIdOrThrow(id);
        repositoryMapper.transferEntityDto(newRepository, repository);
        return this.repository.save(repository);
    }

    public List<Repository> findAllByOwnerId(Long ownerId)
    {
        return repository.findByOwnerId(ownerId);
    }

    public Optional<Repository> findByRootFullName(String rootFullName)
    {
        return repository.findByRootFullName(rootFullName);
    }

    Repository getRepositoryByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new RuntimeException("Tag is not found"); });
    }

    public List<Repository> findAllByFilter(RepositoryFilter filter) {
        return repository.findAllByFilter(filter);
    }
}
