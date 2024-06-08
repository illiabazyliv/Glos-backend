package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.RepositoryDTO;
import com.glos.commentservice.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "repositories")
public interface RepositoryApiClient
{
    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id ,@RequestBody Repository repository);

    @GetMapping("/root-fullName/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);

}
