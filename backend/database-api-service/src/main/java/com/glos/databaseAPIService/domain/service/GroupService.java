package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.entityMappers.GroupMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@Service
public class GroupService implements CrudService<Group, Long>
{
    private final GroupRepository repository;
    private final GroupMapper mapper;

    @Autowired
    public GroupService(GroupRepository repository, GroupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    Group getGroupByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("Access type is not found"));
    }

    @Override
    public Group create(Group group) {
        return repository.save(group);
    }

    @Override
    public List<Group> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Group> getAll(EntityFilter filter) {
        return repository.findAllByFilter(filter);
    }

    @Override
    public Optional<Group> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Group update(Long id, Group newGroup) {
        Group group = getGroupByIdOrThrow(id);
        mapper.transferEntityDto(newGroup, group);
        return repository.save(group);
    }

    @Override
    public void deleteById(Long id) {
        getGroupByIdOrThrow(id);
        repository.deleteById(id);
    }
}
