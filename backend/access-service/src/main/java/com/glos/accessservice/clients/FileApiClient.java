package com.glos.accessservice.clients;

import com.glos.accessservice.responseDTO.FileDTO;
import com.glos.accessservice.responseDTO.Page;
import com.glos.api.entities.File;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@FeignClient(name = "files")
public interface FileApiClient
{

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody File file);

    @GetMapping("/root-fullname/{rootFullName}")
    ResponseEntity<FileDTO> getByRootFullName(@PathVariable String rootFullName);
}
