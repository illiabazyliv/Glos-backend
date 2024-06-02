package com.glos.accessservice.clients;

import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);
}
