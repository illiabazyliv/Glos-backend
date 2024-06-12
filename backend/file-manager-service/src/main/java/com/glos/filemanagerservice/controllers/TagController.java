package com.glos.filemanagerservice.controllers;

import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.TagClient;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.facade.FileApiFacade;
import com.glos.filemanagerservice.facade.RepositoryApiFacade;
import com.glos.filemanagerservice.requestFilters.TagRequestFilter;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping
@RestController
public class TagController
{
    private final FileApiFacade fileApiFacade;
    private final RepositoryApiFacade repositoryApiFacade;
    private final TagClient tagClient;

    public TagController(FileApiFacade fileApiFacade, RepositoryApiFacade repositoryApiFacade, TagClient tagClient) {
        this.fileApiFacade = fileApiFacade;
        this.repositoryApiFacade = repositoryApiFacade;
        this.tagClient = tagClient;
    }

    @GetMapping("/tags")
    public ResponseEntity<Page<Tag>> getTags(@ModelAttribute Tag tag,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                             @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort)
    {
        TagRequestFilter requestFilter = new TagRequestFilter(tag.getId(), tag.getName(), page, size, sort);
        Map<String, Object> filter = MapUtils.map(requestFilter);
        return ResponseEntity.ok(tagClient.getByFilter(filter).getBody());
    }

    @PutMapping("/files/{rootFullName}/add-tag/{name}")
    public ResponseEntity<FileDTO> addFilesTag(@PathVariable("rootFullName") String rootFullName,
                                          @PathVariable("name") String name)
    {
        return ResponseEntity.ok(fileApiFacade.addTag(rootFullName, name).getBody());
    }

    @PutMapping("/files/{rootFullName}/remove-tag/{name}")
    public ResponseEntity<?> removeFilesTag(@PathVariable("rootFullName") String rootFullName,
                                       @PathVariable("name") String name)
    {
        fileApiFacade.removeTag(rootFullName, name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/repositories/{rootFullName}/add-tag/{name}")
    public ResponseEntity<RepositoryDTO> addRepositoryTag(@PathVariable("rootFullName") String rootFullName,
                                                @PathVariable("name") String name)
    {
        return ResponseEntity.ok(repositoryApiFacade.addTag(rootFullName, name).getBody());
    }

    @PutMapping("repositories//{rootFullName}/remove-tag/{name}")
    public ResponseEntity<?> removeRepositoryTag(@PathVariable("rootFullName") String rootFullName,
                                       @PathVariable("name") String name)
    {
        repositoryApiFacade.removeTag(rootFullName, name);
        return ResponseEntity.noContent().build();
    }
}

