package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.validation.OnCreate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<FileAndStatus>> uploadFile(@ModelAttribute @Validated(OnCreate.class) FileRequest uploadRequests)
    {
        return fileApiFacade.uploadFiles(uploadRequests.getFiles());
    }

    @PostMapping("/files/download")
    public ResponseEntity<?> downloadFile(@RequestBody DownloadRequest request)
    {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not implemented");
//        ByteArrayResource resource = fileApiFacade.downloadFiles(request).getBody();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip");
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
//
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/repositories/{rootFullName}/download")
    public ResponseEntity<?> downloadRepository(@PathVariable String rootFullName)
    {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("not implemented");
        //return ResponseEntity.ok(repositoryStorageClient.getRepository(rootFullName).getBody());
    }

}
