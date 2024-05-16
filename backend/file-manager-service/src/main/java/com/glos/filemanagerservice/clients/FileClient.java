package com.glos.filemanagerservice.clients;

import com.glos.api.entities.File;
import com.glos.filemanagerservice.DTO.FileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@FeignClient(name = "files")
public interface FileClient
{
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileByID(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file);

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@RequestBody File newFile, @PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id);

    @GetMapping("/repository/{repositoryId}")
    public  ResponseEntity<List<FileDTO>> getFilesByRepository(@PathVariable Long repositoryId);

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getFilesByFilter(@SpringQueryMap Map<String, Object> filter);
}
