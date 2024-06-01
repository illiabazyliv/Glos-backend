package com.glos.databaseAPIService.domain.controller;


import com.glos.databaseAPIService.domain.entities.Repository;
import com.glos.databaseAPIService.domain.entities.User;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryService;
import com.glos.databaseAPIService.domain.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @GetMapping("/owner-id/{ownerId}")
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable Long ownerId,
                                                                        @ModelAttribute Repository filter,
                                                                        @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
                                                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        if (filter.getOwner() == null) {
            User user = new User();
            user.setId(ownerId);
            filter.setOwner(user);
        } else {
            filter.getOwner().setId(ownerId);
        }
        return getRepositoriesByFilter(filter, ignoreSys, pageable);
    }

    @GetMapping("/owner/{username}")
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable String username,
                                                                        @ModelAttribute Repository filter,
                                                                        @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
                                                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        if (filter.getOwner() == null) {
            User user = new User();
            user.setUsername(username);
            filter.setOwner(user);
        } else {
            filter.getOwner().setUsername(username);
        }
        return getRepositoriesByFilter(filter, ignoreSys, pageable);
    }


    @GetMapping("/root-full-name/{rootFullName}")
    public ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName)
    {
        final String ordinalRootFullName = PathUtils.originalPath(rootFullName);
        Repository rep = repositoryService.findByRootFullName(ordinalRootFullName).orElseThrow(() -> {return new ResourceNotFoundException("Repository is not found");} );
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO = transferEntityDTO(rep, repositoryDTO);
        return ResponseEntity.of(Optional.of(repositoryDTO));
    }

    @GetMapping()
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByFilter(
            @ModelAttribute Repository filter,
            @RequestParam(value = "ignoreSys", required = false, defaultValue = "true") boolean ignoreSys,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
    {
        PathUtils.ordinalPathsRepository(filter);
        Page<Repository> repositories = repositoryService.findAllByFilter(filter, pageable, ignoreSys);

        Page<RepositoryDTO> repositoryDTOS = repositories.map(mapper::toDto);
        return ResponseEntity.ok(repositoryDTOS);
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
