package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.Comment;
import com.glos.databaseAPIService.domain.entityMappers.CommentMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.CommentRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@Service
public class CommentService implements CrudService<Comment, Long> {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(
            CommentRepository commentRepository,
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public Comment create(Comment e) {
        return commentRepository.save(e);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getAll(Comment filter) {
        return commentRepository.findAll(Example.of(filter));
    }

    @Override
    public List<Comment> getAll(EntityFilter filter) {
        return commentRepository.findAllByFilter(filter);
    }

    @Override
    public Optional<Comment> getById(Long aLong) {
        return commentRepository.findById(aLong);
    }

    @Override
    public Comment update(Long aLong, Comment e) {
        Optional<Comment> commentOpt = getById(aLong);
        Comment found = commentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        commentMapper.transferDtoEntity(e, found);
        return commentRepository.save(found);
    }

    @Override
    public void deleteById(Long aLong) {
        Optional<Comment> commentOpt = getById(aLong);
        Comment found = commentOpt.orElseThrow(() ->
                new ResourceNotFoundException("Not found")
        );
        commentRepository.deleteById(found.getId());
    }
}
