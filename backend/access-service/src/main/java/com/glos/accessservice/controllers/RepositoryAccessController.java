package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.RepositoryApiFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RepositoryAccessController
{
    private final RepositoryApiFacade repositoryApiFacade;

    public RepositoryAccessController(RepositoryApiFacade repositoryApiFacade) {
        this.repositoryApiFacade = repositoryApiFacade;
    }

    @PutMapping("/repositories/{rootFullName}/append-access-type/{name}")
    public ResponseEntity<?> appendRepositoryAccessType(@PathVariable("rootFullName") String rootFullName, @PathVariable("name") String name)
    {
        repositoryApiFacade.repositoryAppendAccessType(rootFullName, name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/repositories/{rootFullName}/remove-access-type/{name}")
    public ResponseEntity<?> removeRepositoryAccessType(@PathVariable("rootFullName") String rootFullName, @PathVariable("name") String name)
    {
        repositoryApiFacade.repositoryRemoveAccessType(rootFullName, name);
        return ResponseEntity.noContent().build();
    }
}
