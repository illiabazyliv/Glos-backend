package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.Repository;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/repositories")
public class RepositoryAPIController
{
    private final RepositoryService repositoryService;

    @Autowired
    public RepositoryAPIController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repository> getRepository(@PathVariable Long id)
    {
        return ResponseEntity.of(repositoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Repository> createRepository(@RequestBody Repository repository)
    {
        repositoryService.save(repository);
        return ResponseEntity.created(URI.create("/v1/repositories/"+repository.getId())).body(repository);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable Long id)
    {
        repositoryService.delete(repositoryService.findById(id).get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repository> updateRepository(@RequestBody Repository newRepository, @PathVariable Long id)
    {
        repositoryService.update(newRepository, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ownerId}")
    public List<Repository> getRepositoriesByOwnerId(@PathVariable Long ownerId)
    {
        return repositoryService.findAllByOwnerId(ownerId);
    }

    /*
    * потрібно доповнити
    * */
}
