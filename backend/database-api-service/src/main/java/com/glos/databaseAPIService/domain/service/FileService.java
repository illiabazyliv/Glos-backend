package com.glos.databaseAPIService.domain.service;


import com.accesstools.AccessNode;
import com.glos.databaseAPIService.domain.entities.File;
import com.glos.databaseAPIService.domain.entityMappers.FileMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Objects.requireNonNull(file);
        if (file.getAccessTypes() != null) {
            file.setAccessTypes(
                    file.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
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

    public Optional<File> getByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }

    @Transactional
    @Override
    public File update(Long id, File newFile)
    {
        Objects.requireNonNull(newFile);
        if (newFile.getAccessTypes() != null) {
            newFile.setAccessTypes(
                    newFile.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
        File file = getFileByIdOrThrow(id);
        fileMapper.transferEntityDto(newFile, file);
        return this.fileRepository.save(file);
    }

    @Transactional
    @Override
    public void deleteById(Long id)
    {
        delete(getFileByIdOrThrow(id));
    }

    @Transactional
    public void deleteByRootFullName(String rootFullName)
    {
        delete(getFileByRootFullNameOrThrow(rootFullName));
    }

    @Transactional
    private void delete(File file)
    {
        Objects.requireNonNull(file);
        if (file.getAccessTypes() != null) {
            file.setAccessTypes(
                    file.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
        fileRepository.delete(file);
    }

    File getFileByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
    }

    File getFileByRootFullNameOrThrow(String rootFullName)
    {
        return getByRootFullName(rootFullName).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
    }

    public Page<File> findAllByRepository(File filter, Pageable pageable)
    {
        return fileRepository.findAll(Example.of(filter), pageable);
    }

    public Page<File> findAllByFilter(File filter, Pageable pageable)
    {
        Objects.requireNonNull(filter);
        if (filter.getAccessTypes() != null) {
            filter.setAccessTypes(
                    filter.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).collect(Collectors.toSet())
            );
        }
        Page<File> files = fileRepository.findAll(Example.of(filter), pageable);
        files.getContent().stream()
                .filter(x -> filter.getAccessTypes() == null || x.getAccessTypes().containsAll(filter.getAccessTypes()))
                .filter(x -> filter.getComments() == null || x.getComments().containsAll(filter.getComments()))
                .filter(x -> filter.getSecureCodes() == null || x.getSecureCodes().containsAll(filter.getSecureCodes()));
        return files;
    }

    public Optional<File> findByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }
}
