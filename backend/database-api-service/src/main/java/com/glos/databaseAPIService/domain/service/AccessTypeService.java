package com.glos.databaseAPIService.domain.service;

import com.glos.api.entities.AccessType;
import com.glos.databaseAPIService.domain.entityMappers.AccessTypeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.AccessTypeRepository;
import org.springframework.stereotype.Service;

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
            AccessTypeMapper accessTypeMapper
    ) {
        this.accessTypeRepository = accessTypeRepository;
        this.accessTypeMapper = accessTypeMapper;
    }

    @Override
    public AccessType create(AccessType accessType) {
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

    @Override
    public AccessType update(Long id, AccessType e) {
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeMapper.transferDtoEntity(e, found);
        return accessTypeRepository.save(found);
    }

    @Override
    public void deleteById(Long id) {
        Optional<AccessType> accessTypeOpt = getById(id);
        AccessType found = accessTypeOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        accessTypeRepository.deleteById(found.getId());
    }
}
