package com.glos.commentservice.domain.facade;

import com.glos.commentservice.domain.DTO.CommentDTO;
import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.domain.DTO.Page;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.client.FileApiClient;
import com.glos.commentservice.domain.client.RepositoryApiClient;
import com.glos.commentservice.domain.responseMappers.CommentDTOMapper;
import com.glos.commentservice.domain.responseMappers.FileDTOMapper;
import com.glos.commentservice.domain.responseMappers.FileRequestMapper;
import com.glos.commentservice.domain.responseMappers.UserDTOMapper;
import com.glos.commentservice.entities.Comment;
import com.glos.commentservice.entities.File;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileApiFacade
{
    private final FileApiClient fileClient;
    private final RepositoryApiClient repositoryClient;

    private final FileDTOMapper fileDTOMapper;
    private final FileRequestMapper fileRequestMapper;
    private final UserDTOMapper userDTOMapper;
    private final CommentApiFacade commentApiFacade;
    private final CommentDTOMapper commentDTOMapper;

    public FileApiFacade(FileApiClient fileClient,
                         RepositoryApiClient repositoryClient,
                         FileDTOMapper fileDTOMapper,
                         FileRequestMapper fileRequestMapper,
                         UserDTOMapper userDTOMapper,
                         CommentApiFacade commentApiFacade,
                         CommentDTOMapper commentDTOMapper) {
        this.fileClient = fileClient;
        this.repositoryClient = repositoryClient;
        this.fileDTOMapper = fileDTOMapper;
        this.fileRequestMapper = fileRequestMapper;
        this.userDTOMapper = userDTOMapper;
        this.commentApiFacade = commentApiFacade;
        this.commentDTOMapper = commentDTOMapper;
    }

    public ResponseEntity<Page<CommentDTO>> getFileComments(String rootFullName)
    {
        FileDTO fileDTO = fileClient.getByRootFullName(rootFullName).getBody();
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setContent(fileDTO.getComments());
        return ResponseEntity.ok(commentDTOPage);
    }

    public ResponseEntity<CommentDTO> createFileComment(Comment comment, String rootFullName)
    {
        CommentDTO created = commentApiFacade.createComment(comment).getBody();
        created.getAuthor().setId(comment.getAuthor().getId());
        FileDTO fileDTO = fileClient.getByRootFullName(rootFullName).getBody();
        fileDTO.getComments().add(created);
        File file = fileDTOMapper.toEntity(fileDTO);
        fileClient.update(file.getId(), file);
        return ResponseEntity.ok(created);
    }

    public ResponseEntity<?> deleteComment(String rootFullName, Long id)
    {
        CommentDTO commentDTO = commentApiFacade.getById(id).getBody();
        FileDTO fileDTO = fileClient.getByRootFullName(rootFullName).getBody();
        fileDTO.getComments().remove(commentDTO);
        fileClient.update(fileDTO.getId(), fileDTOMapper.toEntity(fileDTO));
        commentApiFacade.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateComment(String rootFullName, Long id, Comment comment)
    {
        comment.setId(id);
        commentApiFacade.updateComment(id, comment);
        CommentDTO commentDTO = commentApiFacade.getById(id).getBody();
        FileDTO fileDTO = fileClient.getByRootFullName(rootFullName).getBody();
        fileDTO.getComments().stream().forEach((x) -> {
            if(x.getId() == id)
            {
                x = commentDTO;
            }
        });
        fileClient.update(fileDTO.getId(), fileDTOMapper.toEntity(fileDTO));
        return ResponseEntity.noContent().build();
    }
}
