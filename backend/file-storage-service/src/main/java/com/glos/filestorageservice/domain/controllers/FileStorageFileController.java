package com.glos.filestorageservice.domain.controllers;

import com.glos.filestorageservice.domain.DTO.MoveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file-storage/files")
public class FileStorageFileController
{
    @PostMapping
    public ResponseEntity<?> uploadFile()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> downloadFile()
    {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateFile()
    {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveFile(@RequestBody MoveDTO move)
    {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFile()
    {
        return ResponseEntity.noContent().build();
    }
}
