package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.MoveRequest;
import com.glos.filestorageservice.domain.services.RepositoryStorageService;
import io.minio.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> getRepository()
    {
        return ResponseEntity.ok().build();
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
