package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.AccessType;
import com.glos.databaseAPIService.domain.service.AccessTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/access-types")
public class AccessTypeAPIController {

    private final AccessTypeService accessTypeService;

    public AccessTypeAPIController(AccessTypeService accessTypeService) {
        this.accessTypeService = accessTypeService;
    }

    @GetMapping
    public ResponseEntity<List<AccessType>> getAll() {
        return ResponseEntity.ok(accessTypeService.getAll());
    }

    @GetMapping("/{id:\\d}")
    public ResponseEntity<AccessType> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.of(accessTypeService.getById(id));
    }

    @GetMapping("/{name:\\D}")
    public ResponseEntity<AccessType> getByName(
            @PathVariable String name
    ) {
        return ResponseEntity.of(accessTypeService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<AccessType> create(
            @RequestBody AccessType request,
            UriComponentsBuilder uriBuilder
    ) {
        AccessType created = accessTypeService.create(request);
        return ResponseEntity.created(
                    uriBuilder.path("/access-types/{id}")
                        .build(created.getId())
                ).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessType> create(
            @PathVariable Long id,
            @RequestBody AccessType request
    ) {
        accessTypeService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        accessTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
