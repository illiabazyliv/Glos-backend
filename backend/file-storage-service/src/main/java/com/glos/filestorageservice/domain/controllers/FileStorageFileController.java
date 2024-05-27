package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.*;
import com.glos.filestorageservice.domain.services.FileStorageService;
import io.minio.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class FileStorageFileController
{

    private final FileStorageService fileStorageService;

    public FileStorageFileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<FileAndStatus>> uploadFiles(@ModelAttribute UploadRequest request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        return ResponseEntity.ok(fileStorageService.upload(request.getFiles()));
    }

    @GetMapping("/download")
    public ResponseEntity<List<FileWithPath>> downloadFile(@RequestBody DownloadRequest request)
    {
        return ResponseEntity.ok(fileStorageService.download(request.getFilenames()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFile(@RequestBody UpdateRequest request)
    {
        return ResponseEntity.ok(fileStorageService.update(request.getFiles()));
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveFile(@RequestBody MoveRequest request)
    {
        return ResponseEntity.ok(fileStorageService.move(request.getMoves()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody DeleteRequest request)
    {
        return ResponseEntity.ok(fileStorageService.delete(request.getFilenames()));
    }
}
