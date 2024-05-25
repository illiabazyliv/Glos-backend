package com.glos.accessservice.facade;

import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.clients.FileApiClient;
import com.glos.accessservice.entities.AccessType;
import com.glos.accessservice.entities.File;
import com.glos.accessservice.responseDTO.FileDTO;
import com.glos.accessservice.responseMappers.FileDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileApiFacade
{
    private final FileApiClient fileApiClient;
    private final AccessTypeApiClient accessTypeApiClient;
    private final FileDTOMapper fileDTOMapper;

    public FileApiFacade(FileApiClient fileApiClient,
                         AccessTypeApiClient accessTypeApiClient,
                         FileDTOMapper fileDTOMapper) {
        this.fileApiClient = fileApiClient;
        this.accessTypeApiClient = accessTypeApiClient;
        this.fileDTOMapper = fileDTOMapper;
    }

    public ResponseEntity<?> fileAppendAccessType(String rootFullName, String name)
    {
        FileDTO fileDTO = fileApiClient.getByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeApiClient.getByName(name).getBody();
        fileDTO.getAccessTypes().add(accessType);

        File file = fileDTOMapper.toEntity(fileDTO);

        fileApiClient.update(file.getId(), file);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> fileRemoveAccessType(String rootFullName, String name)
    {

        FileDTO fileDTO = fileApiClient.getByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeApiClient.getByName(name).getBody();
        fileDTO.getAccessTypes().removeIf((x) -> {return x.getId().equals(accessType.getId());});

        File file = fileDTOMapper.toEntity(fileDTO);

        fileApiClient.update(file.getId(), file);
        return ResponseEntity.noContent().build();
    }
}
