package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.*;
import com.glos.databaseAPIService.domain.entityMappers.RepositoryMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.RepositoryFilter;
import com.glos.databaseAPIService.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * 	@author - yablonovskydima
 */
@Service
public class RepositoryService
{
    private final RepositoryRepository repository;
    private final UserRepository userRepository;
    private final RepositoryMapper repositoryMapper;
    private final AccessTypeRepository accessTypeRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final FileRepository fileRepository;

    @Autowired
    public RepositoryService(RepositoryRepository repository,
                             RepositoryMapper repositoryMapper,
            UserRepository userRepository,
                             AccessTypeRepository accessTypeRepository,
                             CommentRepository commentRepository,
                             TagRepository tagRepository,
                             FileRepository fileRepository
    ) {
        this.repository = repository;
        this.repositoryMapper = repositoryMapper;
        this.userRepository = userRepository;
        this.accessTypeRepository = accessTypeRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.fileRepository = fileRepository;
    }

    public Optional<Repository> findById(Long id)
    {
        return repository.findById(id);
    }

    @Transactional
    public Repository save(Repository repository)
    {
        assignUser(repository);
        assignComments(repository);
        assignTags(repository);
        assignFiles(repository);
        assignAccessTypes(repository);
        Repository repo =  this.repository.save(repository);
        return repo;
    }

    private Repository assignFiles(Repository repository) {
        final List<File> files = repository.getFiles();
        if (files != null) {
            final List<File> found = repository.getFiles().stream().map(x -> {
                if (x.getId() != null) {
                    return fileRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Repository is not found")
                    );
                }
                return x;
            }).toList();
            repository.setFiles(found);
        }
        return repository;
    }

    private Repository assignTags(Repository repository) {
        final List<Tag> tags = repository.getTags();
        if (tags != null) {
            final List<Tag> found = tags.stream().map(x -> {
                if (x.getId() != null) {
                    return tagRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Id of Tag is not found")
                    );
                }
                return x;
            }).toList();
            repository.setTags(found);
        }
        return repository;
    }

    private Repository assignComments(Repository repository) {
        final List<Comment> comments = repository.getComments();
        if (comments != null) {
            final List<Comment> found = comments.stream().map(x -> {
                if (x.getId() != null) {
                    return commentRepository.findById(x.getId()).orElseThrow(() ->
                            new ResourceNotFoundException("Comment not found")
                    );
                }
                return x;
            }).toList();
            repository.setComments(found);
        }
        return repository;
    }

    private Repository assignUser(Repository repository) {
        final User owner = repository.getOwner();
        if (owner != null) {
            User user = owner;
            if (owner.getId() != null) {
                user = userRepository.findById(owner.getId()).orElseThrow(() ->
                        new ResourceNotFoundException("Id of User is not found")
                );
            } else if (owner.getUsername() != null) {
                user = userRepository.findByUsername(owner.getUsername()).orElseThrow(() ->
                        new ResourceNotFoundException("Username of User is not found")
                );
            } else if (owner.getEmail() != null) {
                user = userRepository.findByEmail(owner.getEmail()).orElseThrow(() ->
                        new ResourceNotFoundException("Email of User is not found")
                );
            } else if (owner.getPhone_number() != null) {
                user = userRepository.findByPhoneNumber(owner.getPhone_number()).orElseThrow(() ->
                        new ResourceNotFoundException("Phone number of User is not found")
                );
            }
            if (user != null) {
                repository.setOwner(user);
            }
        }
        return repository;
    }

    private Repository assignAccessTypes(Repository repository) {
        final List<AccessType> ats = repository.getAccessTypes();
        if (ats != null && !ats.isEmpty()) {
            final List<AccessType> found = ats.stream().map(x -> {
                if (x.getId() != null) {
                    return accessTypeRepository.findById(x.getId()).orElseThrow(() ->
                        new ResourceNotFoundException("Id of AccessType is not found")
                    );
                }
                return x;
            }).toList();
            repository.setAccessTypes(found);
        }
        return repository;
    }

    @Transactional
    public void delete(Repository repository)
    {
        this.repository.delete(repository);
    }

    @Transactional
    public Repository update(Repository newRepository, Long id)
    {
        Repository repository = getRepositoryByIdOrThrow(id);
        assignUser(newRepository);
        assignComments(newRepository);
        assignTags(newRepository);
        assignFiles(newRepository);
        assignAccessTypes(newRepository);
        repositoryMapper.transferEntityDto(newRepository, repository);
        return this.repository.save(repository);
    }

    public List<Repository> findAllByOwnerId(Long ownerId)
    {
        return repository.findByOwnerId(ownerId);
    }

    public Optional<Repository> findByRootFullName(String rootFullName)
    {
        return repository.findByRootFullName(rootFullName);
    }

    Repository getRepositoryByIdOrThrow(Long id)
    {
        return findById(id).orElseThrow(() -> { return new ResourceNotFoundException("Tag is not found"); });
    }

    public List<Repository> findAllByFilter(Repository filter) {
        List<Repository> list = repository.findAll(Example.of(filter));

        return list.stream()
                .filter(x -> filter.getAccessTypes() == null || x.getAccessTypes().containsAll(filter.getAccessTypes()))
                .filter(x -> filter.getComments() == null || x.getComments().containsAll(filter.getComments()))
                .filter(x -> filter.getSecureCodes() == null || x.getSecureCodes().containsAll(filter.getSecureCodes()))
                .filter(x -> filter.getTags() == null || x.getTags().containsAll(filter.getTags()))
                .filter(x -> filter.getFiles() == null || x.getFiles().containsAll(filter.getFiles()))
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        Repository found = getRepositoryByIdOrThrow(id);
        repository.deleteById(found.getId());
    }
}
