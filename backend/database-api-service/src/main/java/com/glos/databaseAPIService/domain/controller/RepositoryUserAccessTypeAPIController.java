package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.Repository;
import com.glos.api.entities.RepositoryUserAccessType;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.RepositoryUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryUserAccessTypeDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.RepositoryUserAccessTypeDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.RepositoryUserAccessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ruat")
public class RepositoryUserAccessTypeAPIController
{
    private final RepositoryUserAccessTypeService service;
    private final RepositoryUserAccessTypeDTOMapper mapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public RepositoryUserAccessTypeAPIController(RepositoryUserAccessTypeService service, RepositoryUserAccessTypeDTOMapper mapper, RepositoryDTOMapper repositoryDTOMapper, UserDTOMapper userDTOMapper) {
        this.service = service;
        this.mapper = mapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepositoryUserAccessTypeDTO> getRUATById(@PathVariable Long id)
    {
        RepositoryUserAccessType ruat = service.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("Repository User Access Type is not found");} );
        RepositoryUserAccessTypeDTO ruatDTO = new RepositoryUserAccessTypeDTO();
        ruatDTO = transferEntityDTO(ruat, ruatDTO);
        return ResponseEntity.of(Optional.of(ruatDTO));
    }

    @PostMapping
    public ResponseEntity<?> createRUAT(@RequestBody RepositoryUserAccessType ruat, UriComponentsBuilder uriBuilder)
    {
        RepositoryUserAccessType r = service.create(ruat);
        RepositoryUserAccessTypeDTO ruatDTO = new RepositoryUserAccessTypeDTO();
        ruatDTO = transferEntityDTO(r, ruatDTO);
        return ResponseEntity.created(
                uriBuilder.path("/ruat/{id}")
                        .build(ruatDTO.getId())).body(ruatDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRUAT(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRUAT(@PathVariable Long id, @RequestBody RepositoryUserAccessType newRUAT)
    {
        service.update(id, newRUAT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public List<RepositoryUserAccessTypeDTO> getAllRUAT()
    {
        List<RepositoryUserAccessType> ruats = service.getAll();
        List<RepositoryUserAccessTypeDTO> ruatsDTO = new ArrayList<>();

        for (RepositoryUserAccessType r:ruats)
        {
            RepositoryUserAccessTypeDTO rDTO = new RepositoryUserAccessTypeDTO();
            rDTO = transferEntityDTO(r, rDTO);
            ruatsDTO.add(rDTO);
        }

        return ruatsDTO;
    }

    @GetMapping("/filter")
    public List<RepositoryUserAccessTypeDTO> getRUATByFilter(RepositoryUserAccessTypeFilter filter)
    {
        List<RepositoryUserAccessType> ruats = service.getAll(filter);
        List<RepositoryUserAccessTypeDTO> ruatsDTO = new ArrayList<>();

        for (RepositoryUserAccessType r:ruats)
        {
            RepositoryUserAccessTypeDTO rDTO = new RepositoryUserAccessTypeDTO();
            rDTO = transferEntityDTO(r, rDTO);
            ruatsDTO.add(rDTO);
        }

        return ruatsDTO;
    }

    RepositoryUserAccessTypeDTO transferEntityDTO(RepositoryUserAccessType source, RepositoryUserAccessTypeDTO destination)
    {
        UserDTO userDTO = new UserDTO();
        userDTOMapper.transferEntityDto(source.getUser(), userDTO);

        Repository rep = source.getRepository();
        UserDTO repUserDTO = new UserDTO();
        userDTOMapper.transferEntityDto(rep.getOwner(), repUserDTO);

        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTOMapper.transferEntityDto(rep, repositoryDTO);
        repositoryDTO.setOwner(repUserDTO);

        mapper.transferEntityDto(source, destination);
        destination.setRepository(repositoryDTO);
        destination.setUser(userDTO);
        return destination;
    }
}
