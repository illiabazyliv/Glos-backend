package com.glos.commentservice.domain.client;

import com.glos.api.entities.File;
import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.domain.DTO.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "files")
public interface FileApiClient
{
    @GetMapping("/{id}")
    ResponseEntity<FileDTO> getFileById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<FileDTO> createFile(@RequestBody File file);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody File file);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<FileDTO> getByRootFullName(@PathVariable String rootFullName);

    @GetMapping("/repository/{repositoryId}")
    ResponseEntity<Page<FileDTO>> getByRepository(@PathVariable Long repositoryId,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    );

    @GetMapping
    ResponseEntity<Page<FileDTO>> getByFilter(@ModelAttribute File file,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                     @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    );
}
