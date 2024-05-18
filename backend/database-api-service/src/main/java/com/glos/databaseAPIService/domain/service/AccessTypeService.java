package com.glos.databaseAPIService.domain.service;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.entityMappers.AccessTypeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.AccessTypeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@Service
public class AccessTypeService implements CrudService<AccessType, Long> {
    private final AccessTypeRepository accessTypeRepository;
    private final AccessTypeMapper accessTypeMapper;

    public AccessTypeService(
            AccessTypeRepository accessTypeRepository,
            AccessTypeMapper accessTypeMapper) {
        this.accessTypeRepository = accessTypeRepository;
        this.accessTypeMapper = accessTypeMapper;
    }

    @Transactional
    @Override
    public AccessType create(AccessType accessType)
    {
        return accessTypeRepository.save(accessType);
    }

    @Override
    public List<AccessType> getAll() {
        return accessTypeRepository.findAll();
    }

    @Override
    public List<AccessType> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<AccessType> getById(Long id) {
        return accessTypeRepository.findById(id);
    }


    public Optional<AccessType> getByName(String name) {
        return accessTypeRepository.findByName(name);
    }

    @Transactional
    @Override
    public AccessType update(Long id, AccessType e) {
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeMapper.transferDtoEntity(e, found);
        return accessTypeRepository.save(found);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeRepository.deleteById(found.getId());
    }

    public Page<AccessType> getPageByFilter(AccessType accessType, Pageable pageable)
    {
        return accessTypeRepository.findAll(Example.of(accessType), pageable);
    }
}
