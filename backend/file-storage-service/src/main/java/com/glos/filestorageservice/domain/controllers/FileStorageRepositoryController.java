package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.MoveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file-storage/repositories")
public class FileStorageRepositoryController
{
    @PostMapping
    public ResponseEntity<?> createRepository()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getRepository()
    {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateRepository()
    {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveRepository(@RequestBody MoveDTO move)
    {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFile()
    {
        return ResponseEntity.noContent().build();
    }
}
