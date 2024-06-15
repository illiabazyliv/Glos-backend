package com.glos.filemanagerservice.facade;

import com.accesstools.AccessNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.clients.RepositoryStorageClient;
import com.glos.filemanagerservice.clients.TagClient;
import com.glos.filemanagerservice.entities.AccessType;
import com.glos.filemanagerservice.entities.Repository;
import com.glos.filemanagerservice.entities.Tag;
import com.glos.filemanagerservice.entities.User;
import com.glos.filemanagerservice.exception.HttpStatusCodeImplException;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestFilterMapper;
import com.glos.filemanagerservice.utils.MapUtils;
import com.pathtools.NodeType;
import com.pathtools.Path;
import com.pathtools.PathParser;
import feign.FeignException;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryApiFacade
{
    private final RepositoryClient repositoryClient;
    private  final RepositoryRequestFilterMapper requestMapper;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final RepositoryStorageClient repositoryStorageClient;
    private final TagClient tagClient;

    public RepositoryApiFacade(RepositoryClient repositoryClient, RepositoryRequestFilterMapper requestMapper, RepositoryDTOMapper repositoryDTOMapper, RepositoryStorageClient repositoryStorageClient, TagClient tagClient) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.repositoryStorageClient = repositoryStorageClient;
        this.tagClient = tagClient;
    }


    public ResponseEntity<RepositoryDTO> create(Repository repository)
    {
        assignPath(repository);
        checkAccessTypes(repository);

        final Path path = PathParser.getInstance().parse(repository.getRootFullName());
        final Path parentRepository = path.reader().parent(NodeType.REPOSITORY);

        final Repository parentFound = repositoryDTOMapper.toEntity(
                repositoryClient.getRepositoryByRootFullName(parentRepository.getPath()).getBody()
        );

        repository.setAccessTypes(parentFound.getAccessTypes());

        final String ownerUsername = path.getFirst().getSimpleName();
        final User owner = (repository.getOwner() != null) ? repository.getOwner() : new User();

        if (owner.getUsername() == null) {
            owner.setUsername(ownerUsername);
        }

        repository.setOwner(owner);

        try
        {
            repository.setCreationDate(LocalDateTime.now());
            repository.setUpdateDate(LocalDateTime.now());
            RepositoryDTO created = repositoryClient.createRepository(repository).getBody();
            //repositoryAndStatus = repositoryStorageClient.createRepository(repository.getRootFullName()).getBody();
            return ResponseEntity.ok(created);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<List<RepositoryAndStatus>> update(List<RepositoryUpdateRequest.RepositoryNode> repositories)
    {
        List<RepositoryAndStatus> repositoryAndStatuses;
        MoveRequest moveRequest = new MoveRequest();
        try {
            for (RepositoryUpdateRequest.RepositoryNode repository : repositories)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                Repository repo = objectMapper.readValue(repository.getRepositoryBody(), Repository.class);

                assignPath(repo);
                checkAccessTypes(repo);

                repo.setId(repository.getId());
                repo.setUpdateDate(LocalDateTime.now());

                String rootFullName = repositoryClient.getRepositoryById(repository.getId()).getBody().getRootFullName();
                repositoryClient.updateRepository(repo, repository.getId());

                if (repo.getRootFullName() != null && !rootFullName.equals(repo.getRootFullName()))
                {
                    moveRequest.getMoves().add(new MoveRequest.MoveNode(rootFullName, repo.getRootFullName()));
                }
            }
            repositoryAndStatuses = repositoryStorageClient.moveRepository(moveRequest).getBody();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok(repositoryAndStatuses);
    }

    public ResponseEntity<?> delete(Long id)
    {
        try {
            //repositoryStorageClient.deleteRepository(repositoryClient.getRepositoryById(id).getBody().getRootFullName());
        } catch (FeignException e) {
            throw new RuntimeException("Internal server error");
        }
        try
        {
            repositoryClient.deleteRepository(id);
        }
        catch (FeignException e)
        {
            throw new HttpStatusCodeImplException(HttpStatusCode.valueOf(e.status()), e.getMessage());
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
        Page<RepositoryDTO> repositoryPage = repositoryClient.getRepositoriesByFilter(map).getBody();
        return ResponseEntity.ok(repositoryPage);
    }

    public ResponseEntity<Page<RepositoryDTO>> getRepositoryByFilter(Repository repository, Map<String, Object> filter, int page, int size, String sort)
    {
        checkAccessTypes(repository);
        RepositoryDTO repositoryDTO = repositoryDTOMapper.toDto(repository);
        RepositoryRequestFilter requestFilter = requestMapper.toDto(repositoryDTO);
        requestFilter.setPage(page);
        requestFilter.setSize(size);
        requestFilter.setSort(sort);

        Map<String, Object> map = MapUtils.map(requestFilter);
        map.putAll(filter);
        map.put("ignoreSys", true);
        map.put("ignoreDefault", true);
        Page<RepositoryDTO> repositories = repositoryClient.getRepositoriesByFilter(map).getBody();
        return ResponseEntity.ok(repositories);
    }

    public ResponseEntity<RepositoryDTO> addTag(String rootFullName, String name)
    {
        Tag tag = new Tag(name);
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getTags().add(tag);
        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
        repositoryClient.updateRepository(repository, repositoryDTO.getId());
        return ResponseEntity.ok(repositoryClient.getRepositoryById(repositoryDTO.getId()).getBody());
    }

    public ResponseEntity<RepositoryDTO> removeTag(String rootFullName, String name)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getTags().removeIf(x -> {return x.getName().equals(name);});
        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
        repositoryClient.updateRepository(repository, repositoryDTO.getId());
        tagClient.deleteTag(tagClient.getTagByName(name).getBody().getId());
        return ResponseEntity.noContent().build();
    }

    private void assignPath(Repository repository) {
        Path path = PathParser.getInstance().parse(repository.getRootPath());
        String rootName = repository.getRootName();
        if (!rootName.startsWith("$")) {
            rootName = '$' + rootName;
        }
        path = path.createBuilder().repository(rootName, false).build();
        repository.setRootName(path.getLast().getRootName());
        repository.setRootPath(path.getLast().getRootPath());
        repository.setRootFullName(path.getLast().getRootFullName());
        repository.setDisplayName(path.getLast().getSimpleName());
        repository.setDisplayPath(path.reader().parent().getSimplePath("/", false));
        repository.setDisplayFullName(path.getSimplePath("/", false));
    }

    private void checkAccessTypes(Repository repository) {
        if (repository.getAccessTypes() != null) {
            repository.setAccessTypes(
                    new ArrayList<>(repository.getAccessTypes().stream()
                            .peek(x -> {
                                AccessNode node = AccessNode.builder(x.getName()).build();
                                x.setName(node.getPattern());
                            }).toList())
            );
        }
    }
}
