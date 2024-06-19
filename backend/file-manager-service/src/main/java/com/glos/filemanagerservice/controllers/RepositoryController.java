package com.glos.filemanagerservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.validation.OnCreate;
import com.glos.filemanagerservice.validation.OnUpdate;
import com.pathtools.Path;
import jakarta.validation.Valid;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import static com.glos.filemanagerservice.DTO.RepositoryUpdateRequest.RepositoryNode;

@RestController
@RequestMapping("/repositories")
public class RepositoryController
{
    private final RepositoryClient repositoryClient;
    private final RepositoryApiFacade repositoryApiFacade;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryRequestMapper repositoryRequestMapper;

    public RepositoryController(RepositoryClient repositoryClient,
                                RepositoryApiFacade repositoryApiFacade,
                                RepositoryDTOMapper repositoryDTOMapper,
                                RepositoryRequestMapper repositoryRequestMapper) {
        this.repositoryClient = repositoryClient;
        this.repositoryApiFacade = repositoryApiFacade;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryRequestMapper = repositoryRequestMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getById(@PathVariable Long id)
    {
        ResponseEntity<RepositoryDTO> response = repositoryClient.getRepositoryById(id);
        RepositoryDTO repository = response.getBody();
        return ResponseEntity.ok(repository);
    }

    @PostMapping
    public ResponseEntity<RepositoryDTO> create(@Valid @RequestBody RepositoryRequest request)
    {
        Repository repository = repositoryRequestMapper.toEntity(request);
        RepositoryDTO created = repositoryApiFacade.create(repository).getBody();
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody RepositoryUpdateDTO request) {
        final RepositoryNode node = new RepositoryNode(id, request);
        repositoryApiFacade.update(Collections.singletonList(node));
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<List<RepositoryAndStatus>> update(@RequestBody @Valid List<RepositoryNode> repositoryNodes) {
        return ResponseEntity.ok(repositoryApiFacade.update(repositoryNodes).getBody());
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
        Map<String, Object> filter = new HashMap<>();
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, filter, page, size, sort).getBody());
    }

    @GetMapping("/root-fullname/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(repositoryClient.getRepositoryByRootFullName(rootFullName).getBody());
    }

    @GetMapping("/path/{rootFullName}")
    public ResponseEntity<Page<RepositoryDTO>> getByFilter(@PathVariable String rootFullName,
                                                           @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                           @RequestParam(name = "accessTypes", required = false, defaultValue = "") List<String> accessTypes,
                                                           @RequestParam(name = "tags", required = false, defaultValue = "") List<String> tags,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        final Path path = Path.builder(rootFullName).build();
        Repository repository = new Repository();
        repository.setRootPath(path.getPath());
        return getByFilter(repository, search, path.getFirst().getSimpleName(), accessTypes, tags, page, size, sort);
    }

    @GetMapping
    public ResponseEntity<Page<RepositoryDTO>> getByFilter(@ModelAttribute Repository repository,
                                                           @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                                           @RequestParam(name = "username", required = false) String username,
                                                           @RequestParam(name = "accessTypes", required = false, defaultValue = "") List<String> accessTypes,
                                                           @RequestParam(name = "tags", required = false, defaultValue = "") List<String> tags,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        Map<String, Object> filter = new HashMap<>();
        putIfNonNull(filter, "owner.username", username);
        putIfNonNull(filter, "search", search);
        if (accessTypes != null && !accessTypes.isEmpty()) {
            putIfNonNull(filter, "accessTypes", accessTypes);
        }
        if (tags != null && !accessTypes.isEmpty()) {
            putIfNonNull(filter, "tags", tags);
        }
        return ResponseEntity.ok(repositoryApiFacade.getRepositoryByFilter(repository, filter, page, size, sort).getBody());
    }

    private void putIfNonNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
