package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.entities.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

public class FileUploadController
{
    private final FileClient fileClient;

    public FileUploadController(FileClient fileClient) {
        this.fileClient = fileClient;
    }

    @PutMapping("/files/{filename}/upload")
    public ResponseEntity<FileDTO> uploadFile(@RequestBody File file, UriComponentsBuilder uriComponentsBuilder)
    {
        FileDTO created = fileClient.createFile(file).getBody();
        return ResponseEntity.created(uriComponentsBuilder.path("/files/{id}")
                .build(created.getId())).body(created);
    }

    @GetMapping("/files/{filename}/download")
    public ResponseEntity<?> downloadFile()
    {
        return ResponseEntity.noContent().build();
    }
}
