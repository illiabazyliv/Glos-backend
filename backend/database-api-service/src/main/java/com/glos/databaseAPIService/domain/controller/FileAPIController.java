package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.File;
import com.glos.api.entities.Repository;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.FileDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.FileService;
import com.glos.databaseAPIService.domain.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public FileAPIController(FileService fileService, FileDTOMapper fileDTOMapper,  RepositoryDTOMapper repositoryDTOMapper, UserDTOMapper userDTOMapper) {
        this.fileService = fileService;
        this.fileDTOMapper = fileDTOMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileByID(@PathVariable Long id)
    {
        FileDTO fileDTO = new FileDTO();
        File file = fileService.getById(id).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        fileDTO = transferEntityDTO(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file, UriComponentsBuilder uriBuilder)
    {
        PathUtils.ordinalPathsFile(file);
        File created = fileService.create(file);
        PathUtils.normalizePathsFile(created);

        FileDTO fileDTO = new FileDTO();
        fileDTO = transferEntityDTO(file, fileDTO);


        return ResponseEntity
                .created(uriBuilder.path("/files/{id}")
                        .build(created.getId())).body(fileDTO);
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
    public  ResponseEntity<List<FileDTO>> getFilesByRepository(@PathVariable Long repositoryId)
    {
        List<File> files = fileService.findAllByRepositoryId(repositoryId);
        return ResponseEntity.of(Optional.of(files.stream().map((x) -> {return transferEntityDTO(x, new FileDTO());}).toList()));
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<FileDTO> getFileByRootFullName(@PathVariable String rootFullName)
    {
        String normalizeRootFullName = PathUtils.originalPath(rootFullName);
        File file = fileService.findByRootFullName(normalizeRootFullName).orElseThrow(() -> { return new ResourceNotFoundException("File is not found"); });
        FileDTO fileDTO = new FileDTO();
        fileDTO = transferEntityDTO(file, fileDTO);
        return ResponseEntity.of(Optional.of(fileDTO));
    }

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getFilesByFilter(@ModelAttribute File filter)
    {
        PathUtils.ordinalPathsFile(filter);
        List<File> files = fileService.findAllByFilter(filter);
        return ResponseEntity.of(Optional.of(files.stream().map((x) -> {return transferEntityDTO(x, new FileDTO());}).toList()));
    }

    FileDTO transferEntityDTO(File source, FileDTO destination)
    {
        RepositoryDTO rep = new RepositoryDTO();
        rep = transferEntityDTORep(source.getRepository(), rep);
        fileDTOMapper.transferEntityDto(source, destination);
        destination.setRepository(rep);
        return destination;
    }
    RepositoryDTO transferEntityDTORep(Repository source, RepositoryDTO destination)
    {
        UserDTO owner = new UserDTO();
        userDTOMapper.transferEntityDto(source.getOwner(), owner);
        repositoryDTOMapper.transferEntityDto(source, destination);
        destination.setOwner(owner);
        return destination;
    }
}
