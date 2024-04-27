package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Tag;
import com.glos.databaseAPIService.domain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * 	@author - yablonovskydima
 */
@RestController
@RequestMapping("/tags")
public class TagAPIController
{
    private final TagService tagService;

    @Autowired
    public TagAPIController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id)
    {
        return ResponseEntity.of(tagService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag)
    {
        tagService.create(tag);
        return ResponseEntity.created(URI.create("/v1/tags/"+tag.getId())).body(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id)
    {
        tagService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@PathVariable("id") Long id ,@RequestBody Tag newTag)
    {
        tagService.update(id, newTag);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name)
    {
        return ResponseEntity.of(tagService.getByName(name));
    }

    @GetMapping
    public List<Tag> getAllTags()
    {
        return tagService.getAll();
    }
}
