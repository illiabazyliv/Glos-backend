package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entity.Tag;
import com.glos.databaseAPIService.domain.entityMappers.TagMapper;
import com.glos.databaseAPIService.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService
{
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public Optional<Tag> findById(Long id)
    {
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag)
    {
        return tagRepository.save(tag);
    }

    public void delete(Tag tag)
    {
        tagRepository.delete(tag);
    }

    public Tag update(Tag newTag, Long id)
    {
        Tag tag = getTagByIdOrThrow(id);
        tagMapper.transferEntityDto(newTag, tag);
        return tagRepository.save(tag);
    }

    public Optional<Tag> findByName(String name)
    {
        return tagRepository.findByName(name);
    }

    Tag getTagByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new RuntimeException("Tag is not found"); });
    }

}
