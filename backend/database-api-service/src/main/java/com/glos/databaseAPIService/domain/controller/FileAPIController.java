package com.glos.databaseAPIService.domain.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.api.entities.AccessType;
import com.glos.api.entities.File;
import com.glos.api.entities.Repository;
import com.glos.databaseAPIService.domain.filters.FileFilter;
import com.glos.databaseAPIService.domain.service.FileService;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/files")
public class FileAPIController
{
    private final FileService fileService;

    @Autowired
    public FileAPIController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileByID(@PathVariable Long id)
    {
        return ResponseEntity.of(fileService.findById(id));
    }

    @GetMapping("/json")
    public ResponseEntity<String> getJson(RepositoryService service) {
        File file = new File(
                null,
                "/root/to",
                "file.txt",
                "/root/to/file.txt",
                100,
                "txt",
                "/root/to",
                "file.txt",
                "/root/to/file.txt",
                service.findById(1L).get(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );
        try {
            return ResponseEntity.ok(new ObjectMapper().writeValueAsString(file));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createFile(@RequestBody File file)
    {
        System.out.println(file);
        fileService.save(file);
        return ResponseEntity.created(URI.create("/v1/files/"+file.getId())).body(file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@RequestBody File newFile, Long id)
    {
        fileService.update(newFile, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id)
    {
        fileService.delete(fileService.findById(id).get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repository/{repositoryId}")
    public List<File> getFilesByRepository(@PathVariable Long repositoryId)
    {
        return fileService.findAllByRepositoryId(repositoryId);
    }

    @GetMapping("/{rootFullName}")
    public ResponseEntity<File> getFileByRootFullName(@PathVariable String rootFullName)
    {
        return ResponseEntity.of(fileService.findByRootFullName(rootFullName));
    }

    @GetMapping()
    public List<File> getFilesByFilter(@ModelAttribute FileFilter filter)
    {
        return fileService.findAllByFilter(filter);
    }

}
