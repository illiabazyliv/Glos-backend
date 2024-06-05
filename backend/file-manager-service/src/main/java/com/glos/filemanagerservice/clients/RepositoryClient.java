package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.entities.Repository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "repositories")
public interface RepositoryClient
{
    @GetMapping("/{id}")
    public ResponseEntity<Repository> getRepositoryById(@PathVariable Long id);
    @PostMapping
    public ResponseEntity<Repository> createRepository(@RequestBody Repository repository);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRepository(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id);

    @GetMapping("/owner-id/{ownerId}")
    public ResponseEntity<List<Repository>> getRepositoriesByOwnerId(@PathVariable Long ownerId);

    @GetMapping()
    public ResponseEntity<Page<Repository>> getRepositoriesByFilter(@SpringQueryMap Map<String, Object> filter);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);

}
