package com.glos.filemanagerservice.controllers;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Repository;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.DTO.UserDTO;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/repositories")
public class RepositoryController
{
    private final RepositoryClient repositoryClient;
    private  final RepositoryRequestMapper requestMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryApiFacade repositoryApiFacade;

    public RepositoryController(RepositoryClient repositoryClient,
                                RepositoryRequestMapper requestMapper,
                                RepositoryDTOMapper repositoryDTOMapper,
                                RepositoryApiFacade repositoryApiFacade) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryApiFacade = repositoryApiFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(repositoryClient.getRepositoryById(id).getBody());
    }

    @PostMapping
    public ResponseEntity<RepositoryDTO> create(@RequestBody Repository repository, UriComponentsBuilder uriComponentsBuilder)
    {
        RepositoryDTO created = repositoryClient.createRepository(repository).getBody();
        return ResponseEntity.created(uriComponentsBuilder.path("/repositories/{id}")
                .build(created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository)
    {
        repository.setId(id);
        repositoryClient.updateRepository(repository, repository.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repositoryClient.deleteRepository(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner-id/{ownerId}")
    public ResponseEntity<Page<RepositoryDTO>> getByOwnerId(@PathVariable Long ownerId,
                                                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByOwnerId(ownerId, page, size, sort).getBody());
    }

    @GetMapping("/root-fullName/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryClient.getRepositoryByRootFullName(rootFullName).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<RepositoryDTO>> getByFilter(@ModelAttribute Repository repository,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, page, size, sort).getBody());
    }
}
