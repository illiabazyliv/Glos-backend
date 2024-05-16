package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
public class RepositoryController
{
    private final RepositoryClient repositoryClient;

    private final RepositoryDTOMapper repositoryDTOMapper;

    public RepositoryController(RepositoryClient repositoryClient, RepositoryDTOMapper repositoryDTOMapper) {
        this.repositoryClient = repositoryClient;
        this.repositoryDTOMapper = repositoryDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryDTO> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok(repositoryClient.getRepositoryById(id).getBody());
    }
}
