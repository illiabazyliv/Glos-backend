package com.glos.filemanagerservice.facade;

import com.glos.api.entities.AccessType;
import com.glos.api.entities.Comment;
import com.glos.api.entities.Repository;
import com.glos.api.entities.User;
import com.glos.filemanagerservice.DTO.*;
import com.glos.filemanagerservice.clients.AccessTypeClient;
import com.glos.filemanagerservice.clients.CommentAPIClient;
import com.glos.filemanagerservice.clients.RepositoryClient;
import com.glos.filemanagerservice.requestFilters.RepositoryRequestFilter;
import com.glos.filemanagerservice.responseMappers.RepositoryDTOMapper;
import com.glos.filemanagerservice.responseMappers.RepositoryRequestMapper;
import com.glos.filemanagerservice.responseMappers.UserDTOMapper;
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
    private final CommentAPIClient commentAPIClient;
    private final UserDTOMapper userDTOMapper;

    public RepositoryApiFacade(RepositoryClient repositoryClient,
                               RepositoryRequestMapper requestMapper,
                               RepositoryDTOMapper repositoryDTOMapper,
                               AccessTypeClient accessTypeClient, CommentAPIClient commentAPIClient, UserDTOMapper userDTOMapper) {
        this.repositoryClient = repositoryClient;
        this.requestMapper = requestMapper;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.accessTypeClient = accessTypeClient;
        this.commentAPIClient = commentAPIClient;
        this.userDTOMapper = userDTOMapper;
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

    public ResponseEntity<?> repositoryRemoveAccessType(String rootFullName, String name)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        AccessType accessType = accessTypeClient.getByName(name).getBody();
        repositoryDTO.getAccessTypes().remove(accessType);

        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);

        repositoryClient.updateRepository(repository, repository.getId());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<CommentDTO>> getRepositoryComments(String rootFullName)
    {
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setContent(repositoryDTO.getComments());
        return ResponseEntity.ok(commentDTOPage);
    }

    public ResponseEntity<CommentDTO> createRepositoryComment(Comment comment, String rootFullName)
    {
        CommentDTO created = commentAPIClient.createComment(comment).getBody();
        created.getAuthor().setId(comment.getAuthor().getId());
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().add(created);
        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
        repositoryClient.updateRepository(repository, repository.getId());
        return ResponseEntity.ok(created);
    }

    public ResponseEntity<?> deleteComment(String rootFullName, Long id)
    {
        CommentDTO commentDTO = commentAPIClient.getById(id).getBody();
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().remove(commentDTO);
        repositoryClient.updateRepository(repositoryDTOMapper.toEntity(repositoryDTO), repositoryDTO.getId());
        commentAPIClient.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateComment(String rootFullName, Long id, Comment comment)
    {
        comment.setId(id);
        commentAPIClient.updateComment(id, comment);
        CommentDTO commentDTO = commentAPIClient.getById(id).getBody();
        RepositoryDTO repositoryDTO = repositoryClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().stream().forEach((x) -> {
            if(x.getId() == id)
            {
                x = commentDTO;
            }
        });
        repositoryClient.updateRepository(repositoryDTOMapper.toEntity(repositoryDTO), repositoryDTO.getId());
        return ResponseEntity.noContent().build();
    }
}
