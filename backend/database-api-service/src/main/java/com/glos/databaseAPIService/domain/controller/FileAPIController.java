package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.File;
import com.glos.databaseAPIService.domain.service.FileService;
import com.glos.databaseAPIService.domain.util.PathUtils;
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
        return ResponseEntity.of(fileService.getById(id));
    }

    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody File file)
    {
        PathUtils.ordinalPathsFile(file);
        File created = fileService.create(file);
        PathUtils.normalizePathsFile(created);
        return ResponseEntity.created(URI.create("/v1/files/"+file.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<File> updateFile(@RequestBody File newFile, @PathVariable("id") Long id)
    {
        PathUtils.ordinalPathsFile(newFile);
        fileService.update(id, newFile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id)
    {
        fileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repository/{repositoryId}")
    public List<File> getFilesByRepository(@PathVariable Long repositoryId)
    {
        return fileService.findAllByRepositoryId(repositoryId);
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<File> getFileByRootFullName(@PathVariable String rootFullName)
    {
        String normalizeRootFullName = PathUtils.originalPath(rootFullName);
        return ResponseEntity.of(fileService.findByRootFullName(normalizeRootFullName));
    }

    @GetMapping()
    public ResponseEntity<List<File>> getFilesByFilter(@ModelAttribute File filter)
    {
        PathUtils.ordinalPathsFile(filter);
        List<File> files = fileService.findAllByFilter(filter);
        return ResponseEntity.ok(files);
    }
}
