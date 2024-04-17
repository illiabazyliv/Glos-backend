package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.File;
import com.glos.databaseAPIService.domain.entity.Repository;
import com.glos.databaseAPIService.domain.entityMappers.FileMapper;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import com.glos.databaseAPIService.domain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class FileService
{
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Autowired
    public FileService(FileRepository fileRepository, FileMapper fileMapper)
    {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    public Optional<File> findById(Long id)
    {
        return fileRepository.findById(id);
    }

    public File save(File file)
    {
        return fileRepository.save(file);
    }

    public void delete(File file)
    {
        fileRepository.delete(file);
    }

    public File update(File newFile, Long id)
    {
        File file = getFileByIdOrThrow(id);
        fileMapper.transferEntityDto(newFile, file);
        return this.fileRepository.save(file);
    }
    File getFileByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new RuntimeException("Tag is not found"); });
    }

    public List<File> findAllByRepositoryId(Long id)
    {
        return fileRepository.findAllByRepositoryId(id);
    }

    public List<File> findAllByFilter(FileFilter filter)
    {
        return fileRepository.findAllByFilter(filter);
    }

    public Optional<File> findByRootFullName(String rootFullName)
    {
        return fileRepository.findByRootFullName(rootFullName);
    }

}
