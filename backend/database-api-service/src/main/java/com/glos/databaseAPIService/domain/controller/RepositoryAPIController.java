package com.glos.databaseAPIService.domain.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.api.entities.Repository;
import com.glos.api.entities.User;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryFilterMapper;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryMapper;
import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import com.glos.databaseAPIService.domain.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/repositories")
public class RepositoryAPIController
{
    private final RepositoryService repositoryService;

    @Autowired
    public RepositoryAPIController(
            RepositoryService repositoryService
    ) {
        this.repositoryService = repositoryService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Repository> getRepository(@PathVariable Long id)
    {

        return ResponseEntity.of(repositoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Repository> createRepository(@RequestBody Repository repository) {
        PathUtils.ordinalPathsRepository(repository);
        Repository repo = repositoryService.save(repository);
        PathUtils.normalizePathsRepository(repository);
        return ResponseEntity
                .created(URI.create("/v1/repositories/"+repo.getId()))
                .body(repo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable Long id)
    {
        repositoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repository> updateRepository(@RequestBody Repository newRepository, @PathVariable Long id)
    {
        PathUtils.ordinalPathsRepository(newRepository);
        repositoryService.update(newRepository, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("owner-id/{ownerId}")
    public List<Repository> getRepositoriesByOwnerId(@PathVariable Long ownerId)
    {
        return repositoryService.findAllByOwnerId(ownerId);
    }


    @GetMapping("root-full-name/{rootFullName}")
    public ResponseEntity<Repository> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        final String ordinalRootFullName = PathUtils.originalPath(rootFullName);
        return ResponseEntity.of(repositoryService.findByRootFullName(ordinalRootFullName));
    }

    @GetMapping()
    public List<Repository> getRepositoriesByFilter(@ModelAttribute Repository filter)
    {
        PathUtils.ordinalPathsRepository(filter);
        return repositoryService.findAllByFilter(filter);
    }
}
