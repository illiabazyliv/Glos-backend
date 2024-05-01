package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.File;
import com.glos.databaseAPIService.domain.entityMappers.FileMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import com.glos.databaseAPIService.domain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class FileService implements CrudService<File, Long>
{
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Autowired
    public FileService(FileRepository fileRepository, FileMapper fileMapper)
    {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    @Transactional
    public File create(File file)
    {
        return fileRepository.save(file);
    }

    @Override
    public List<File> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public List<File> getAll(EntityFilter filter) {
       throw  new UnsupportedOperationException();
    }

    @Override
    public Optional<File> getById(Long id)
    {
        return fileRepository.findById(id);
    }

    @Transactional
    @Override
    public File update(Long id, File newFile)
    {
        File file = getFileByIdOrThrow(id);
        fileMapper.transferEntityDto(newFile, file);
        return this.fileRepository.save(file);
    }

    @Transactional
    @Override
    public void deleteById(Long id)
    {
        File found = getFileByIdOrThrow(id);
        fileRepository.delete(found);
    }

    File getFileByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
    }

    public List<File> findAllByRepositoryId(Long id)
    {
        return fileRepository.findAllByRepositoryId(id);
    }

    public List<File> findAllByFilter(File filter)
    {
        List<File> files = fileRepository.findAll(Example.of(filter));
        return files.stream()
                .filter(x -> filter.getAccessTypes() == null || x.getAccessTypes().containsAll(filter.getAccessTypes()))
                .filter(x -> filter.getComments() == null || x.getComments().containsAll(filter.getComments()))
                .filter(x -> filter.getSecureCodes() == null || x.getSecureCodes().containsAll(filter.getSecureCodes()))
                .toList();
    }

    public Optional<File> findByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }
}
