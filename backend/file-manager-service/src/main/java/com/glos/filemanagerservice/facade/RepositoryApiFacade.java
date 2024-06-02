package com.glos.filemanagerservice.facade;

import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import com.pathtools.Path;
import com.pathtools.PathParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryApiFacade
{
    private final RepositoryClient repositoryClient;
    private  final RepositoryRequestMapper requestMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryStorageClient repositoryStorageClient;

    public RepositoryApiFacade(RepositoryClient repositoryClient, RepositoryRequestMapper requestMapper, RepositoryDTOMapper repositoryDTOMapper, RepositoryStorageClient repositoryStorageClient) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryStorageClient = repositoryStorageClient;
    }

    public ResponseEntity<RepositoryDTO> create(Repository repository)
    {
        Path path = PathParser.getInstance().parse(repository.getRootPath());
        assignPath(repository, path);
        RepositoryDTO repositoryDTO = new RepositoryDTO();
        try
        {
            repository.setCreationDate(LocalDateTime.now());
            repositoryDTO = repositoryClient.createRepository(repository).getBody();
            repositoryStorageClient.createRepository(repository.getRootFullName());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(repositoryDTO);
    }

    public ResponseEntity<?> update(Long id, Repository repository)
    {
        Path path = PathParser.getInstance().parse(repository.getRootPath());
        assignPath(repository, path);
        try {
            repository.setId(id);
            repository.setUpdateDate(LocalDateTime.now());
            String rootFullName = repositoryClient.getRepositoryById(id).getBody().getRootFullName();
            repositoryClient.updateRepository(repository, id);

            if (repository.getRootFullName() != null && !rootFullName.equals(repository.getRootFullName()))
            {
                MoveRequest moveRequest = new MoveRequest();
                moveRequest.getMoves().add(new MoveRequest.MoveNode(rootFullName, repository.getDisplayFullName()));
                repositoryStorageClient.moveRepository(moveRequest);
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> delete(Long id)
    {
        try
        {
            repositoryStorageClient.deleteRepository(repositoryClient.getRepositoryById(id).getBody().getRootFullName());
            repositoryClient.deleteRepository(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
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
        map.put("ignoreSys", true);
        Page<RepositoryDTO> repositories = repositoryClient.getRepositoriesByFilter(map).getBody();
        return ResponseEntity.ok(repositories);
    }

    private void assignPath(Repository repository, Path path) {
        path = path.createBuilder().repository(repository.getRootName(), false).build();
        repository.setRootName(path.getLast().getRootName());
        repository.setRootPath(path.getLast().getRootPath());
        repository.setRootFullName(path.getLast().getRootFullName());
        repository.setDisplayName(path.getLast().getSimpleName());
        repository.setDisplayPath(path.reader().parent().getSimplePath("/", false));
        repository.setDisplayFullName(path.getSimplePath("/", false));
    }
}
