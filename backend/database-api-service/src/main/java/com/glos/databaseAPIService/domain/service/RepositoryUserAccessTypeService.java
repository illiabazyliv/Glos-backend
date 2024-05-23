package com.glos.databaseAPIService.domain.service;


import com.glos.databaseAPIService.domain.entities.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryUserAccessTypeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.RepositoryUserAccessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@Service
public class RepositoryUserAccessTypeService implements CrudService<RepositoryUserAccessType, Long>
{
    private final RepositoryUserAccessTypeRepository repository;
    private final RepositoryUserAccessTypeMapper mapper;

    @Autowired
    public RepositoryUserAccessTypeService(RepositoryUserAccessTypeRepository repository, RepositoryUserAccessTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    RepositoryUserAccessType getRepositoryUserAccessTypeByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("Access type is not found"));
    }

    @Transactional
    @Override
    public RepositoryUserAccessType create(RepositoryUserAccessType rep) {
        return repository.save(rep);
    }

    @Override
    public List<RepositoryUserAccessType> getAll() {
        return repository.findAll();
    }

    @Override
    public List<RepositoryUserAccessType> getAll(EntityFilter filter) {
        return repository.findAllByFilter(filter);
    }

    @Override
    public Optional<RepositoryUserAccessType> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public RepositoryUserAccessType update(Long id, RepositoryUserAccessType newRep) {
        RepositoryUserAccessType rep = getRepositoryUserAccessTypeByIdOrThrow(id);
        mapper.transferEntityDto(newRep, rep);
        return repository.save(rep);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getRepositoryUserAccessTypeByIdOrThrow(id);
        repository.deleteById(id);
    }
}
