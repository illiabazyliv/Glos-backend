package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Repository;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import com.glos.databaseAPIService.domain.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/repositories")
public class RepositoryAPIController
{
    private final RepositoryService repositoryService;
    private final RepositoryDTOMapper mapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public RepositoryAPIController(
            RepositoryService repositoryService,
            RepositoryDTOMapper mapper, UserDTOMapper userDTOMapper) {
        this.repositoryService = repositoryService;

        this.mapper = mapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getRepository(@PathVariable Long id)
    {
        Repository repository = repositoryService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("Repository is not found");} );
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO = transferEntityDTO(repository, repositoryDTO);
        return ResponseEntity.of(Optional.of(repositoryDTO));
    }

    @PostMapping
    public ResponseEntity<RepositoryDTO> createRepository(@RequestBody Repository repository, UriComponentsBuilder uriBuilder) {
        PathUtils.ordinalPathsRepository(repository);
        Repository repo = repositoryService.create(repository);
        PathUtils.normalizePathsRepository(repository);

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO = transferEntityDTO(repo, repositoryDTO);

        return ResponseEntity
                .created(uriBuilder.path("/repositories/{id}")
                        .build(repo.getId())).body(repositoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable Long id)
    {
        repositoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id)
    {
        //PathUtils.ordinalPathsRepository(newRepository);
        repositoryService.update(id, newRepository);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("owner-id/{ownerId}")
    public ResponseEntity<List<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable Long ownerId)
    {
        List<Repository> repositories = repositoryService.findAllByOwnerId(ownerId);
        List<RepositoryDTO> repositoryDTOS = new ArrayList<>();

        return ResponseEntity.ok(repositories.stream().map((x) -> {return transferEntityDTO(x, new RepositoryDTO());}).toList());
    }


    @GetMapping("root-full-name/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        final String ordinalRootFullName = PathUtils.originalPath(rootFullName);
        Repository rep = repositoryService.findByRootFullName(ordinalRootFullName).orElseThrow(() -> {return new ResourceNotFoundException("Repository is not found");} );
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO = transferEntityDTO(rep, repositoryDTO);
        return ResponseEntity.of(Optional.of(repositoryDTO));
    }

    @GetMapping()
    public ResponseEntity<List<RepositoryDTO>> getRepositoriesByFilter(@ModelAttribute Repository filter)
    {
        PathUtils.ordinalPathsRepository(filter);
        List<Repository> repositories = repositoryService.findAllByFilter(filter);

        return ResponseEntity.ok(repositories.stream().map((x) -> {return transferEntityDTO(x, new RepositoryDTO());}).toList());
    }

    RepositoryDTO transferEntityDTO(Repository source, RepositoryDTO destination)
    {
        UserDTO owner = new UserDTO();
        userDTOMapper.transferEntityDto(source.getOwner(), owner);
        mapper.transferEntityDto(source, destination);
        destination.setOwner(owner);
        return destination;
    }
}
