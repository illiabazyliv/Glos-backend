package com.glos.filemanagerservice.facade;

import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.entities.Repository;
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

    public RepositoryApiFacade(RepositoryClient repositoryClient, RepositoryRequestMapper requestMapper, RepositoryDTOMapper repositoryDTOMapper) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
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
}
