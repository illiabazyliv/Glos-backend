package com.glos.accessservice.controllers;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.api.entities.AccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/access-types")
public class AccessTypeController
{
    private final AccessTypeApiClient accessTypeApiClient;

    public AccessTypeController(AccessTypeApiClient accessTypeApiClient) {
        this.accessTypeApiClient = accessTypeApiClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessType> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(accessTypeApiClient.getById(id).getBody());
    }

    @PostMapping
    public ResponseEntity<AccessType> create(@RequestBody AccessType accessType, UriComponentsBuilder uriComponentsBuilder)
    {
        AccessType created = accessTypeApiClient.create(accessType).getBody();
        return ResponseEntity.created(
                uriComponentsBuilder
                        .path("/access-types/{id}")
                        .build(created.getId()))
                .body(created);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<AccessType> getById(@PathVariable String name)
    {
        return ResponseEntity.ok(accessTypeApiClient.getByName(name).getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AccessType accessType)
    {
        accessType.setId(id);
        accessTypeApiClient.update(accessType.getId(), accessType);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        accessTypeApiClient.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
