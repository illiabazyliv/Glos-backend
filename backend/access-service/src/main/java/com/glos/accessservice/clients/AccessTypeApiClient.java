package com.glos.accessservice.clients;

import com.glos.api.entities.AccessType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@FeignClient(name = "access-types")
public interface AccessTypeApiClient
{
    @GetMapping("/{id}")
    public ResponseEntity<AccessType> getById(@PathVariable Long id);

    @GetMapping("/name/{name}")
    public ResponseEntity<AccessType> getByName(@PathVariable String name);

    @PostMapping
    public ResponseEntity<AccessType> create(@RequestBody AccessType request);

    @PutMapping("/{id}")
    public ResponseEntity<AccessType> update(@PathVariable Long id, @RequestBody AccessType request);


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id);
}
