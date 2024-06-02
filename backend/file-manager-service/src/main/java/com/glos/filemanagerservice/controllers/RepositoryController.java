package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/repositories")
public class RepositoryController
{
    private final RepositoryClient repositoryClient;
    private final RepositoryApiFacade repositoryApiFacade;

    public RepositoryController(RepositoryClient repositoryClient,
                                RepositoryApiFacade repositoryApiFacade) {
        this.repositoryClient = repositoryClient;
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
        RepositoryDTO created = repositoryApiFacade.create(repository).getBody();
        return ResponseEntity.created(uriComponentsBuilder.path("/repositories/{id}")
                .build(created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository)
    {

        repositoryApiFacade.update(id, repository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repositoryApiFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<Page<RepositoryDTO>> getByOwnerId(@PathVariable String username,
                                                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        Repository repository = new Repository();
        User user = new User();
        user.setUsername(username);
        repository.setOwner(user);
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, page, size, sort).getBody());
    }

    @GetMapping("/root-fullname/{rootFullName}")
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
