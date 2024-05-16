package com.glos.filemanagerservice.controllers;

import com.glos.api.entities.File;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/files")
public class FileController
{
    private final FileClient fileClient;
    private final FileDTOMapper fileDTOMapper;


    public FileController(FileClient fileClient, FileDTOMapper fileDTOMapper) {
        this.fileClient = fileClient;
        this.fileDTOMapper = fileDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable Long id)
    {
        return ResponseEntity.ok(fileClient.getFileByID(id).getBody());
    }

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file, UriComponentsBuilder uriComponentsBuilder)
    {
        FileDTO created = fileClient.createFile(file).getBody();
        return ResponseEntity.created(uriComponentsBuilder.path("/files/{id}")
                .build(created.getId())).body(created);
    }
}
