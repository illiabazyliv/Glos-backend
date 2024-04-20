package com.glos.databaseAPIService.domain.service;

import com.glos.databaseAPIService.domain.entityMappers.TagMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.glos.api.entities.Tag;

import java.util.List;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class TagService implements CrudService<Tag, Long>
{
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public Optional<Tag> getByName(String name)
    {
        return tagRepository.findByName(name);
    }

    Tag getTagByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> { return new ResourceNotFoundException("Tag is not found"); });
    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getAll(EntityFilter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id)
    {
        getTagByIdOrThrow(id);
        tagRepository.deleteById(id);
    }
    @Override
    public Optional<Tag> getById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag update(Long id, Tag newTag) {
        Tag tag = getTagByIdOrThrow(id);
        tagMapper.transferEntityDto(newTag, tag);
        return tagRepository.save(tag);
    }
}
