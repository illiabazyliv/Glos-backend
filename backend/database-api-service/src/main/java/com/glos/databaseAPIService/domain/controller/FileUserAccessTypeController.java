package com.glos.databaseAPIService.domain.controller;


import com.glos.api.entities.FileUserAccessType;
import com.glos.databaseAPIService.domain.exceptions.ResourceNotFoundException;
import com.glos.databaseAPIService.domain.filters.FileUserAccessTypeFilter;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import com.glos.databaseAPIService.domain.responseDTO.FileUserAccessTypeDTO;
import com.glos.databaseAPIService.domain.responseDTO.UserDTO;
import com.glos.databaseAPIService.domain.responseMappers.FileDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.FileUserAccessTypeDTOMapper;
import com.glos.databaseAPIService.domain.responseMappers.UserDTOMapper;
import com.glos.databaseAPIService.domain.service.FileUserAccessTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mykola Melnyk
 */
@RestController
@RequestMapping("/fuat")
public class FileUserAccessTypeController {

    private final FileUserAccessTypeService fileUserAccessTypeService;
    private final FileUserAccessTypeDTOMapper fileUserAccessTypeDTOMapper;
    private final FileDTOMapper fileDTOMapper;
    private final UserDTOMapper userDTOMapper;

    public FileUserAccessTypeController(
            FileUserAccessTypeService fileUserAccessTypeService,
            FileUserAccessTypeDTOMapper fileUserAccessTypeDTOMapper, FileDTOMapper fileDTOMapper, UserDTOMapper userDTOMapper) {
        this.fileUserAccessTypeService = fileUserAccessTypeService;
        this.fileUserAccessTypeDTOMapper = fileUserAccessTypeDTOMapper;
        this.fileDTOMapper = fileDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<FileUserAccessTypeDTO>> getAll(
            @ModelAttribute FileUserAccessTypeFilter fuatFilter
    )
    {
        List<FileUserAccessType> fuats = fileUserAccessTypeService.getAll(fuatFilter);


        return ResponseEntity.ok(fuats.stream().map((x) -> {return transferEntityDTO(x, new FileUserAccessTypeDTO());}).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileUserAccessTypeDTO> getById(
            @PathVariable Long id
    )
    {
        FileUserAccessType f = fileUserAccessTypeService.getById(id).orElseThrow(() -> {return new ResourceNotFoundException("File User Access Type is not found");} );
        FileUserAccessTypeDTO fd = new FileUserAccessTypeDTO();
        fd = transferEntityDTO(f, fd);
        return ResponseEntity.of(Optional.of(fd));
    }

    @PostMapping
    public ResponseEntity<FileUserAccessTypeDTO> create(
            @RequestBody FileUserAccessType request,
            UriComponentsBuilder uriBuilder
    ) {
        FileUserAccessType created = fileUserAccessTypeService.create(request);

        FileUserAccessTypeDTO fd = new FileUserAccessTypeDTO();
        fd = transferEntityDTO(created, fd);

        return ResponseEntity.created(
                uriBuilder.path("/fuat/{id}")
                        .build(created.getId())
        ).body(fd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody FileUserAccessType request
    ) {
        fileUserAccessTypeService.update(id, request);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        fileUserAccessTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    FileUserAccessTypeDTO transferEntityDTO(FileUserAccessType source, FileUserAccessTypeDTO destination)
    {
        FileDTO fileDTO = new FileDTO();
        UserDTO userDTO = new UserDTO();
        fileDTOMapper.transferEntityDto(source.getFile(), fileDTO);
        userDTOMapper.transferEntityDto(source.getUser(), userDTO);

        fileUserAccessTypeDTOMapper.transferEntityDto(source, destination);
        destination.setUser(userDTO);
        destination.setFile(fileDTO);
        return destination;
    }
}
