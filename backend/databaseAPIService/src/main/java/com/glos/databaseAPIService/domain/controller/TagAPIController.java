package com.glos.databaseAPIService.domain.controller;

import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
        return ResponseEntity.of(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag)
    {
        tagService.save(tag);
        return ResponseEntity.created(URI.create("/v1/tags/"+tag.getId())).body(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id)
    {
        tagService.delete(tagService.findById(id).get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@RequestBody Tag newTag, Long id)
    {
        tagService.update(newTag, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Tag> findTagByName(@PathVariable String name)
    {
        return ResponseEntity.of(tagService.findByName(name));
    }

}
