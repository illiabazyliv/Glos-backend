package com.glos.filemanagerservice.facade;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.File;
import com.glos.api.entities.Repository;
import com.glos.filemanagerservice.DTO.Page;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.DTO.UserDTO;
import com.glos.filemanagerservice.clients.AccessTypeClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RepositoryApiFacade
{
    private final RepositoryClient repositoryClient;
    private  final RepositoryRequestMapper requestMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final AccessTypeClient accessTypeClient;

    public RepositoryApiFacade(RepositoryClient repositoryClient,
                               RepositoryRequestMapper requestMapper,
                               RepositoryDTOMapper repositoryDTOMapper,
                               AccessTypeClient accessTypeClient) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.accessTypeClient = accessTypeClient;
    }

    public ResponseEntity<Page<RepositoryDTO>> getRepositoryByOwnerId(Long ownerId, int page, int size, String sort)
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(ownerId);
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        repositoryDTO.setOwner(userDTO);
        RepositoryRequestFilter requestFilter = requestMapper.toDto(repositoryDTO);
        requestFilter.setPage(page);
        requestFilter.setSize(size);
        requestFilter.setSort(sort);

        Map<String, Object> map = MapUtils.map(requestFilter);
        return ResponseEntity.ok(repositoryClient.getRepositoriesByFilter(map).getBody());
    }

    public ResponseEntity<Page<RepositoryDTO>> getRepositoryByFilter(Repository repository, int page, int size, String sort)
    {
        RepositoryDTO repositoryDTO = repositoryDTOMapper.toDto(repository);
        RepositoryRequestFilter requestFilter = requestMapper.toDto(repositoryDTO);
        requestFilter.setPage(page);
        requestFilter.setSize(size);
        requestFilter.setSort(sort);

        Map<String, Object> map = MapUtils.map(requestFilter);
        return ResponseEntity.ok(repositoryClient.getRepositoriesByFilter(map).getBody());
    }

    public ResponseEntity<?> repositoryAppendAccessType(String rootFullName, String name)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeClient.getByName(name).getBody();
        repositoryDTO.getAccessTypes().add(accessType);

        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);

        repositoryClient.updateRepository(repository, repository.getId());
        return ResponseEntity.noContent().build();
    }
}
