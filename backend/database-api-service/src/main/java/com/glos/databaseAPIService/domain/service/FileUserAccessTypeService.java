package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.FileUserAccessType;
import com.glos.databaseAPIService.domain.entityMappers.FileUserAccessTypeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.filters.FileUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.repository.FileUserAccessTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@Service
public class FileUserAccessTypeService implements CrudService<FileUserAccessType, Long> {
    private final FileUserAccessTypeRepository fileUserAccessTypeRepository;
    private final FileUserAccessTypeMapper fileUserAccessTypeMapper;

    public FileUserAccessTypeService(
            FileUserAccessTypeRepository fileUserAccessTypeRepository,
            FileUserAccessTypeMapper fileUserAccessTypeMapper
    ) {
        this.fileUserAccessTypeRepository = fileUserAccessTypeRepository;
        this.fileUserAccessTypeMapper = fileUserAccessTypeMapper;
    }

    @Override
    public FileUserAccessType create(FileUserAccessType e) {
        return fileUserAccessTypeRepository.save(e);
    }

    @Override
    public List<FileUserAccessType> getAll() {
        return fileUserAccessTypeRepository.findAll();
    }

    @Override
    public List<FileUserAccessType> getAll(EntityFilter filter) {
        return fileUserAccessTypeRepository.findAllByFilter((FileUserAccessTypeFilter) filter);
    }

    @Override
    public Optional<FileUserAccessType> getById(Long aLong) {
        return fileUserAccessTypeRepository.findById(aLong);
    }

    @Override
    public FileUserAccessType update(Long aLong, FileUserAccessType e) {
        FileUserAccessType found = getById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        fileUserAccessTypeMapper.transferDtoEntity(e, found);
        return fileUserAccessTypeRepository.save(found);
    }

    @Override
    public void deleteById(Long aLong) {
        getById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        fileUserAccessTypeRepository.deleteById(aLong);
    }
}
