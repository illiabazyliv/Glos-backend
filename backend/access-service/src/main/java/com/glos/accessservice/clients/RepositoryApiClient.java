package com.glos.accessservice.clients;

import com.glos.accessservice.responseDTO.Page;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import com.glos.api.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository);

    @GetMapping("/root-fullName/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);
}
