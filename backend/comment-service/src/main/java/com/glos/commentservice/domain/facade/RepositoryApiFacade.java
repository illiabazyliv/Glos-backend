package com.glos.commentservice.domain.facade;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Repository;
import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.DTO.RepositoryDTO;
import com.glos.commentservice.domain.client.RepositoryApiClient;
import com.glos.commentservice.domain.responseMappers.RepositoryDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RepositoryApiFacade
{
    private final RepositoryApiClient repositoryApiClient;
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final CommentApiFacade commentApiFacade;

    public RepositoryApiFacade(RepositoryApiClient repositoryApiClient, RepositoryDTOMapper repositoryDTOMapper, CommentApiFacade commentApiFacade) {
        this.repositoryApiClient = repositoryApiClient;
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.commentApiFacade = commentApiFacade;
    }

    public ResponseEntity<Page<CommentDTO>> getRepositoryComments(String rootFullName)
    {
        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setContent(repositoryDTO.getComments());
        return ResponseEntity.ok(commentDTOPage);
    }

    public ResponseEntity<CommentDTO> createRepositoryComment(Comment comment, String rootFullName)
    {
        CommentDTO created = commentApiFacade.createComment(comment).getBody();
        created.getAuthor().setId(comment.getAuthor().getId());
        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().add(created);
        Repository repository = repositoryDTOMapper.toEntity(repositoryDTO);
        repositoryApiClient.update(repository.getId(), repository);
        return ResponseEntity.ok(created);
    }

    public ResponseEntity<?> deleteComment(String rootFullName, Long id)
    {
        CommentDTO commentDTO = commentApiFacade.getById(id).getBody();
        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().remove(commentDTO);
        repositoryApiClient.update(repositoryDTO.getId(), repositoryDTOMapper.toEntity(repositoryDTO));
        commentApiFacade.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateComment(String rootFullName, Long id, Comment comment)
    {
        comment.setId(id);
        commentApiFacade.updateComment(id, comment);
        CommentDTO commentDTO = commentApiFacade.getById(id).getBody();
        RepositoryDTO repositoryDTO = repositoryApiClient.getRepositoryByRootFullName(rootFullName).getBody();
        repositoryDTO.getComments().stream().forEach((x) -> {
            if(x.getId() == id)
            {
                x = commentDTO;
            }
        });
        repositoryApiClient.update(repositoryDTO.getId(), repositoryDTOMapper.toEntity(repositoryDTO));
        return ResponseEntity.noContent().build();
    }
}
