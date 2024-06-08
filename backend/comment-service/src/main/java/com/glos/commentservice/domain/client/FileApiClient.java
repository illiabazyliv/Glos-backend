package com.glos.commentservice.domain.client;

import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.entities.File;
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
