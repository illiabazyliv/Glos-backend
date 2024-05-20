package com.glos.filemanagerservice.controllers;

import com.glos.api.entities.Comment;
import com.glos.api.entities.File;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.requestFilters.FileRequestFilter;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController
{
    private final FileClient fileClient;
    private final RepositoryClient repositoryClient;

    private final FileDTOMapper fileDTOMapper;
    private final FileRequestMapper fileRequestMapper;

    private final FileApiFacade fileApiFacade;


    public FileController(FileClient fileClient,
                          RepositoryClient repositoryClient,
                          FileDTOMapper fileDTOMapper, FileRequestMapper fileRequestMapper,
                          FileApiFacade fileApiFacade) {
        this.fileClient = fileClient;
        this.repositoryClient = repositoryClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileRequestMapper = fileRequestMapper;
        this.fileApiFacade = fileApiFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable Long id)
    {
        return ResponseEntity.ok(fileClient.getFileByID(id).getBody());
    }

    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody File file, UriComponentsBuilder uriComponentsBuilder)
    {
        FileDTO created = fileClient.createFile(file).getBody();
        return ResponseEntity.created(uriComponentsBuilder.path("/files/{id}")
                .build(created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody File file)
    {
        file.setId(id);
        fileClient.updateFile(file, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        fileClient.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/root-fullname/{rootFullName}")
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
