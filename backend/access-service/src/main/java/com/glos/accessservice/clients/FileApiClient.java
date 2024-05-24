package com.glos.accessservice.clients;

import com.glos.accessservice.entities.File;
import com.glos.accessservice.responseDTO.FileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "files")
public interface FileApiClient
{

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody File file);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<FileDTO> getByRootFullName(@PathVariable String rootFullName);
}
