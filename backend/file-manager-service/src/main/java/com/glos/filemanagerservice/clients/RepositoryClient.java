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
    public ResponseEntity<RepositoryDTO> getRepositoryById(@PathVariable Long id);
    @PostMapping
    public ResponseEntity<RepositoryDTO> createRepository(@RequestBody Repository repository);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable Long id);

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRepository(@RequestBody Repository newRepository, @PathVariable("id") Long id);

    @GetMapping("/owner-id/{ownerId}")
    public ResponseEntity<List<RepositoryDTO>> getRepositoriesByOwnerId(@PathVariable Long ownerId);

    @GetMapping()
    public ResponseEntity<Page<RepositoryDTO>> getRepositoriesByFilter(@SpringQueryMap Map<String, Object> filter);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<RepositoryDTO> getRepositoryByRootFullName(@PathVariable String rootFullName);

}
