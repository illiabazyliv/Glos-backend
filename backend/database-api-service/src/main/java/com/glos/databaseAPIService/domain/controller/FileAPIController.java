package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.File;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import com.glos.databaseAPIService.domain.responseMappers.FileDTOMapper;
import com.glos.databaseAPIService.domain.service.FileService;
import com.glos.databaseAPIService.domain.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/files")
public class FileAPIController
{
    private final FileService fileService;
    private final FileDTOMapper fileDTOMapper;

    @Autowired
    public FileAPIController(FileService fileService, FileDTOMapper fileDTOMapper) {
        this.fileService = fileService;
        this.fileDTOMapper = fileDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileByID(@PathVariable Long id)
    {
        FileDTO fileDTO = new FileDTO();
        File file = fileService.getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        fileDTOMapper.transferEntityDto(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file)
    {
        PathUtils.ordinalPathsFile(file);
        File created = fileService.create(file);
        PathUtils.normalizePathsFile(created);

        FileDTO fileDTO = new FileDTO();
        fileDTOMapper.transferEntityDto(created, fileDTO);

        return ResponseEntity.created(URI.create("/files/"+fileDTO.getId())).body(fileDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@RequestBody File newFile, @PathVariable("id") Long id)
    {
        PathUtils.ordinalPathsFile(newFile);
        fileService.update(id, newFile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id)
    {
        fileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repository/{repositoryId}")
    public List<FileDTO> getFilesByRepository(@PathVariable Long repositoryId)
    {
        List<File> files = fileService.findAllByRepositoryId(repositoryId);
        List<FileDTO> fileDTOS = new ArrayList<>();

        for (File file : files)
        {
            FileDTO fileDTO = new FileDTO();
            fileDTOMapper.transferEntityDto(file, fileDTO);
            fileDTOS.add(fileDTO);
        }

        return fileDTOS;
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<FileDTO> getFileByRootFullName(@PathVariable String rootFullName)
    {
        String normalizeRootFullName = PathUtils.originalPath(rootFullName);
        File file = fileService.findByRootFullName(normalizeRootFullName).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        FileDTO fileDTO = new FileDTO();
        fileDTOMapper.transferEntityDto(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getFilesByFilter(@ModelAttribute File filter)
    {
        PathUtils.ordinalPathsFile(filter);
        List<File> files = fileService.findAllByFilter(filter);
        List<FileDTO> fileDTOS = new ArrayList<>();

        for (File file:files)
        {
            FileDTO fileDTO = new FileDTO();
            fileDTOMapper.transferEntityDto(file, fileDTO);
            fileDTOS.add(fileDTO);
        }

        return ResponseEntity.ok(fileDTOS);
    }

    //everything works
}
