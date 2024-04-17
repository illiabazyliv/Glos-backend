package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.entity.SecureCode;
import com.glos.databaseAPIService.domain.filters.RepositoryUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.service.RepositoryUserAccessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ruat")
public class RepositoryUserAccessTypeAPIController
{
    private final RepositoryUserAccessTypeService service;

    @Autowired
    public RepositoryUserAccessTypeAPIController(RepositoryUserAccessTypeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryUserAccessType> getRUATById(@PathVariable Long id)
    {
        return ResponseEntity.of(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createRUAT(@RequestBody RepositoryUserAccessType ruat)
    {
        service.create(ruat);
        return ResponseEntity.created(URI.create("/v1/groups/"+ruat.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRUAT(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRUAT(@PathVariable Long id, @RequestBody RepositoryUserAccessType newRUAT)
    {
        service.update(id, newRUAT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public List<RepositoryUserAccessType> getAllRUAT()
    {
        return service.getAll();
    }

    @GetMapping("/filter")
    public List<RepositoryUserAccessType> getRUATByFilter(RepositoryUserAccessTypeFilter filter)
    {
        return service.getAll(filter);
    }
}
