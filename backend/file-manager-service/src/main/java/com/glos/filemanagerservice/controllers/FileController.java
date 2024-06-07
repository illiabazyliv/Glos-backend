package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.FileRequest;
import com.glos.filemanagerservice.entities.File;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.clients.FileClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController
{
    private final FileClient fileClient;
    private final FileApiFacade fileApiFacade;


    public FileController(FileClient fileClient,
                          FileApiFacade fileApiFacade) {
        this.fileClient = fileClient;
        this.fileApiFacade = fileApiFacade;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable Long id)
    {
        return ResponseEntity.ok(fileClient.getFileByID(id).getBody());
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute FileRequest updateRequest)
    {
        fileApiFacade.update(id, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody List<String> rootFullNames)
    {
        fileApiFacade.deleteFiles(rootFullNames);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{rootFullName}")
    public ResponseEntity<FileDTO> getByRootFullName(@PathVariable String rootFullName)
    {
        return ResponseEntity.ok(fileClient.getFileByRootFullName(rootFullName).getBody());
    }

    @GetMapping("/repository/{repositoryId}")
    public ResponseEntity<Page<FileDTO>> getByRepository(@PathVariable Long repositoryId,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                         @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {

        return ResponseEntity.ok(fileApiFacade.getFileByRepository(repositoryId, page, size, sort).getBody());
    }

    @GetMapping
    public ResponseEntity<Page<FileDTO>> getByFilter(@ModelAttribute File file,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                     @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort
    )
    {
        return ResponseEntity.ok(fileApiFacade.getFilesByFilter(file, page, size, sort).getBody());
    }
}
