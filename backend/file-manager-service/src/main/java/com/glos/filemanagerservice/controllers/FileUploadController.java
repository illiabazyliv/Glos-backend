package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glos.filemanagerservice.DTO.DownloadRequest;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.FileRequest;
import com.glos.filemanagerservice.DTO.UploadRequest;
import com.glos.filemanagerservice.clients.FileStorageClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping
public class FileUploadController
{
    private final FileApiFacade fileApiFacade;
    private final RepositoryStorageClient repositoryStorageClient;

    public FileUploadController(FileApiFacade fileClient, RepositoryStorageClient repositoryStorageClient) {
        this.fileApiFacade = fileClient;
        this.repositoryStorageClient = repositoryStorageClient;
    }



    @PutMapping("/files/upload")
    public ResponseEntity<List<FileDTO>> uploadFile(@ModelAttribute FileRequest fileRequests) throws JsonProcessingException
    {

        List<FileDTO> created = fileApiFacade.uploadFiles(fileRequests.getFileNodes()).getBody();
        return ResponseEntity.ok(created);
    }

    @GetMapping("/files/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestBody DownloadRequest request)
    {
        return ResponseEntity.ok(fileApiFacade.downloadFiles(request).getBody());
    }

    @GetMapping("/repositories/{rootFullName}/download")
    public ResponseEntity<ByteArrayResource> downloadRepository(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryStorageClient.getRepository(rootFullName).getBody());
    }

}
