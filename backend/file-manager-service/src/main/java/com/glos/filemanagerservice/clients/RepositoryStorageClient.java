package com.glos.filemanagerservice.clients;

import com.glos.filemanagerservice.DTO.MoveRequest;
import com.glos.filemanagerservice.DTO.RepositoryAndStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(name = "repositoryStorage")
public interface RepositoryStorageClient
{
    @PostMapping("/{rootFullName}")
    ResponseEntity<RepositoryAndStatus> createRepository(@PathVariable String rootFullName);

    @GetMapping("/download/{rootFullName}")
    ResponseEntity<ByteArrayResource> getRepository(@PathVariable String rootFullName);
    @PostMapping("/move")
    ResponseEntity<List<RepositoryAndStatus>> moveRepository(@RequestBody MoveRequest moves);

    @DeleteMapping("/{rootFullName}")
    ResponseEntity<RepositoryAndStatus> deleteRepository(@PathVariable String rootFullName);
}
