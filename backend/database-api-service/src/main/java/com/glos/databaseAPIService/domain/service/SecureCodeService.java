package com.glos.databaseAPIService.domain.service;


import com.glos.api.entities.SecureCode;
import com.glos.databaseAPIService.domain.entityMappers.SecureCodeMapper;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import com.glos.databaseAPIService.domain.repository.SecureCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SecureCodeService implements CrudService<SecureCode, Long>
{
    private final SecureCodeRepository repository;
    private final SecureCodeMapper mapper;

    @Autowired
    public SecureCodeService(SecureCodeRepository repository, SecureCodeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    SecureCode getSecureCodeByIdOrThrow(Long id)
    {
        return getById(id).orElseThrow(() -> new ResourceNotFoundException("Access type is not found"));
    }

    @Transactional
    @Override
    public SecureCode create(SecureCode secureCode) {
        return repository.save(secureCode);
    }

    @Override
    public List<SecureCode> getAll() {
        return repository.findAll();
    }

    @Override
    public List<SecureCode> getAll(EntityFilter filter)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<SecureCode> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<SecureCode> getByReceiverAndResourcePath(String receiver, String resourcePath)
    {
        return repository.getByReceiverAndResourcePath(receiver, resourcePath);
    }

    @Transactional
    @Override
    public SecureCode update(Long id, SecureCode newSecureCode) {
        SecureCode secureCode = getSecureCodeByIdOrThrow(id);
        mapper.transferEntityDto(newSecureCode, secureCode);
        return repository.save(secureCode);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getSecureCodeByIdOrThrow(id);
        repository.deleteById(id);
    }
}
