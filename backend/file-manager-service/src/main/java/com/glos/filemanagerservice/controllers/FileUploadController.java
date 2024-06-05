package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.DownloadRequest;
import com.glos.filemanagerservice.DTO.FileDTO;
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
    private final FileApiFacade fileClient;
    private final RepositoryStorageClient repositoryStorageClient;

    public FileUploadController(FileApiFacade fileClient, RepositoryStorageClient repositoryStorageClient) {
        this.fileClient = fileClient;
        this.repositoryStorageClient = repositoryStorageClient;
    }


    //TODO переробит логіку, не можна використовувати RequestBody і ModelAttribute одночасно
    @PutMapping("/files/upload")
    public ResponseEntity<List<FileDTO>> uploadFile(@RequestBody List<File> files, @ModelAttribute List<MultipartFile> filesData)
    {

        List<FileDTO> created = fileClient.uploadFiles(files, filesData).getBody();
        return ResponseEntity.ok(created);
    }

    @GetMapping("/files/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestBody List<String> rootFullNames)
    {
        return ResponseEntity.ok(fileClient.downloadFiles(rootFullNames).getBody());
    }

    @GetMapping("/repositories/{rootFullName}/download")
    public ResponseEntity<ByteArrayResource> downloadRepository(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryStorageClient.getRepository(rootFullName).getBody());
    }

}
