package com.glos.filemanagerservice.facade;

import com.glos.api.entities.*;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.clients.AccessTypeClient;
import com.glos.filemanagerservice.clients.CommentAPIClient;
import com.glos.filemanagerservice.clients.FileClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.requestFilters.FileRequestFilter;
import com.glos.filemanagerservice.responseMappers.FileDTOMapper;
import com.glos.filemanagerservice.responseMappers.FileRequestMapper;
import com.glos.filemanagerservice.responseMappers.UserDTOMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileApiFacade
{
    private final FileClient fileClient;
    private final RepositoryClient repositoryClient;

    private final FileDTOMapper fileDTOMapper;
    private final FileRequestMapper fileRequestMapper;
    private final AccessTypeClient accessTypeClient;
    private final UserDTOMapper userDTOMapper;
    private final CommentAPIClient commentAPIClient;

    public FileApiFacade(FileClient fileClient,
                         RepositoryClient repositoryClient,
                         FileDTOMapper fileDTOMapper,
                         FileRequestMapper fileRequestMapper,
                         AccessTypeClient accessTypeClient,
                         UserDTOMapper userDTOMapper,
                         CommentAPIClient commentAPIClient) {
        this.fileClient = fileClient;
        this.repositoryClient = repositoryClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileRequestMapper = fileRequestMapper;
        this.accessTypeClient = accessTypeClient;
        this.userDTOMapper = userDTOMapper;
        this.commentAPIClient = commentAPIClient;
    }

    public ResponseEntity<Page<FileDTO>> getFileByRepository(Long repositoryId, int page, int size, String sort)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryById(repositoryId).getBody();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setRepository(repositoryDTO);
        FileRequestFilter filter = new FileRequestFilter();
        fileRequestMapper.transferEntityDto(fileDTO, filter);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);

        Map<String, Object> map = MapUtils.map(filter);
        return ResponseEntity.ok(fileClient.getFilesByFilter(map).getBody());
    }

    public ResponseEntity<Page<FileDTO>> getFilesByFilter(File file, int page, int size, String sort)
    {
        FileDTO fileDTO = fileDTOMapper.toDto(file);
        FileRequestFilter filter = fileRequestMapper.toDto(fileDTO);
        filter.setPage(page);
        filter.setSize(size);
        filter.setSort(sort);

        Map<String, Object> map = MapUtils.map(filter);
        return ResponseEntity.ok(fileClient.getFilesByFilter(map).getBody());
    }

    public ResponseEntity<?> fileAppendAccessType(String rootFullName, String name)
    {
        FileDTO fileDTO = fileClient.getFileByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeClient.getByName(name).getBody();
        fileDTO.getAccessTypes().add(accessType);

        File file = fileDTOMapper.toEntity(fileDTO);

        fileClient.updateFile(file, file.getId());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> fileRemoveAccessType(String rootFullName, String name)
    {
        FileDTO fileDTO = fileClient.getFileByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeClient.getByName(name).getBody();
        fileDTO.getAccessTypes().remove(accessType);

        File file = fileDTOMapper.toEntity(fileDTO);

        fileClient.updateFile(file, file.getId());
        return ResponseEntity.noContent().build();
    }
}
