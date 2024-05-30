package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.services.RepositoryStorageService;
import com.glos.filestorageservice.domain.utils.ZipUtil;
import io.minio.errors.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/storage/repositories")
public class FileStorageRepositoryController
{
    private final RepositoryStorageService repositoryStorageService;

    public FileStorageRepositoryController(RepositoryStorageService repositoryStorageService) {
        this.repositoryStorageService = repositoryStorageService;
    }

    @PostMapping("/{rootFullName}")
    public ResponseEntity<?> createRepository(@PathVariable String rootFullName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        return ResponseEntity.ok(repositoryStorageService.create(rootFullName));
    }

    @GetMapping("/download/{rootFullName}")
    public ResponseEntity<ByteArrayResource> getRepository(@PathVariable String rootFullName) throws Exception
    {
       try {
           Map<String, Object> filesData = repositoryStorageService.download(rootFullName);
           Map<String, String> fileNames = new HashMap<>();

           for (String key: filesData.keySet())
           {
               fileNames.put(key, key.substring(rootFullName.length() + 1));
           }

           byte[] zipFile = ZipUtil.createRepositoryZip(filesData, fileNames);
           ByteArrayResource resource = new ByteArrayResource(zipFile);

           HttpHeaders headers = new HttpHeaders();
           headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + rootFullName + ".zip");
           headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

           return new ResponseEntity<>(resource, headers, HttpStatus.OK);
       }
       catch (Exception e)
       {
           return ResponseEntity.internalServerError().build();
       }
    }

    @PutMapping("/{rootFullName}/{newName}")
    public ResponseEntity<?> updateRepository(@PathVariable("rootFullName") String rootFullName, @PathVariable("newName") String newName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        return ResponseEntity.ok(repositoryStorageService.rename(rootFullName, newName));
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveRepository(@RequestBody MoveRequest moves) throws Exception {
        return ResponseEntity.ok(repositoryStorageService.move(moves.getMoves()));
    }

    @DeleteMapping("/{rootFullName}")
    public ResponseEntity<?> deleteFile(@PathVariable String rootFullName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.ok(repositoryStorageService.delete(rootFullName));
    }
}
