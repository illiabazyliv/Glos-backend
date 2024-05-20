package com.glos.commentservice.domain.client;

import com.glos.api.entities.Repository;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.DTO.RepositoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @GetMapping("/{id}")
    ResponseEntity<RepositoryDTO> getById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<RepositoryDTO> create(@RequestBody Repository repository, UriComponentsBuilder uriComponentsBuilder);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @GetMapping("/root-fullName/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);

    @GetMapping("/owner-id/{ownerId}")
    ResponseEntity<Page<RepositoryDTO>> getByOwnerId(@PathVariable Long ownerId,
                                                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort);
    @GetMapping
    ResponseEntity<Page<RepositoryDTO>> getByFilter(@ModelAttribute Repository repository,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                           @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort);
}
