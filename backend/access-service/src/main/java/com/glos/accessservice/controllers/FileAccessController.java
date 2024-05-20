package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.FileApiFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FileAccessController
{

    private final FileApiFacade fileApiFacade;

    public FileAccessController(FileApiFacade fileApiFacade) {
        this.fileApiFacade = fileApiFacade;
    }

    @PutMapping("/files/{rootFullName}/append-access-type/{name}")
    public ResponseEntity<?> appendFileAccessType(@PathVariable("rootFullName") String rootFullName, @PathVariable("name") String name)
    {
        fileApiFacade.fileAppendAccessType(rootFullName, name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/files/{rootFullName}/remove-access-type/{name}")
    public ResponseEntity<?> removeFileAccessType(@PathVariable("rootFullName") String rootFullName, @PathVariable("name") String name)
    {
        fileApiFacade.fileRemoveAccessType(rootFullName, name);
        return ResponseEntity.noContent().build();
    }
}
