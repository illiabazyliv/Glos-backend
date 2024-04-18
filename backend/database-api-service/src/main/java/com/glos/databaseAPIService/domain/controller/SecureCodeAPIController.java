package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.SecureCode;
import com.glos.databaseAPIService.domain.service.SecureCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/secure-codes")
public class SecureCodeAPIController
{
    private final SecureCodeService service;

    @Autowired
    public SecureCodeAPIController(SecureCodeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecureCode> getSecureCodeById(@PathVariable Long id)
    {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createSecureCode(@RequestBody SecureCode secureCode)
    {
        service.create(secureCode);
        return ResponseEntity.created(URI.create("/v1/groups/"+secureCode.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSecureCode(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSecureCode(@PathVariable Long id, @RequestBody SecureCode newSecureCode)
    {
        service.update(id, newSecureCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public List<SecureCode> getAllSecureCode()
    {
        return service.getAll();
    }
}
