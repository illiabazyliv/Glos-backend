package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.FileUserAccessType;
import com.glos.databaseAPIService.domain.filters.FileUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.service.FileUserAccessTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/fuat")
public class FileUserAccessTypeController {

    private final FileUserAccessTypeService fileUserAccessTypeService;

    public FileUserAccessTypeController(
            FileUserAccessTypeService fileUserAccessTypeService
    ) {
        this.fileUserAccessTypeService = fileUserAccessTypeService;
    }

    @GetMapping
    public ResponseEntity<List<FileUserAccessType>> getAll(
            @ModelAttribute FileUserAccessTypeFilter fuatFilter
    ) {
        return ResponseEntity.ok(fileUserAccessTypeService.getAll(fuatFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileUserAccessType> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.of(fileUserAccessTypeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<FileUserAccessType> create(
            @RequestBody FileUserAccessType request,
            UriComponentsBuilder uriBuilder
    ) {
        FileUserAccessType created = fileUserAccessTypeService.create(request);
        return ResponseEntity.created(
                uriBuilder.path("/comments/{id}")
                        .build(created.getId())
        ).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileUserAccessType> create(
            @PathVariable Long id,
            @RequestBody FileUserAccessType request
    ) {
        fileUserAccessTypeService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        fileUserAccessTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
