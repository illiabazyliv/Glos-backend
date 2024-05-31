package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.*;
import com.glos.filestorageservice.domain.services.FileStorageService;
import com.glos.filestorageservice.domain.utils.ZipUtil;
import io.minio.errors.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    public ResponseEntity<List<FileAndStatus>> uploadFiles(@ModelAttribute UploadRequest request)
    {
        List<FileAndStatus> fileAndStatuses = new ArrayList<>();

        try
        {
            fileAndStatuses = fileStorageService.upload(request.getFiles());
        }
        catch (Exception e)
        {
             throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok(fileAndStatuses);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestBody DownloadRequest request)
    {
        try
        {
            List<byte[]> filesData = fileStorageService.download(request.getFilenames());

            //TODO імплементувати парсер
            List<String> fileNames = request.getFilenames().stream()
                    .map(path -> path.substring(path.lastIndexOf("/") + 1))
                    .toList();
            //TODO імплементувати парсер

            byte[] zipData = ZipUtil.createZip(filesData, fileNames);

            ByteArrayResource resource = new ByteArrayResource(zipData);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files.zip");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFile(@ModelAttribute FileWithPath request)
    {
        return ResponseEntity.ok(fileStorageService.update(request));
    }

    @PostMapping("/move")
    public ResponseEntity<List<FileAndStatus>> moveFile(@RequestBody MoveRequest request) throws Exception
    {
        return ResponseEntity.ok(fileStorageService.move(request.getMoves()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<FileAndStatus>> deleteFile(@RequestBody DeleteRequest request)
    {
        return ResponseEntity.ok(fileStorageService.delete(request.getFilenames()));
    }
}
