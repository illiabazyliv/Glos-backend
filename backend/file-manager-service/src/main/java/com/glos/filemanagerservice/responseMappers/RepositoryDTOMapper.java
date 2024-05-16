package com.glos.filemanagerservice.responseMappers;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Repository;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.RepositoryDTO;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryDTOMapper extends AbstractMapper<RepositoryDTO, Repository>
{

    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;

    public RepositoryDTOMapper(UserDTOMapper userDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.userDTOMapper = userDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
    }


    protected void postDtoCopy(Repository source, RepositoryDTO destination)
    {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).toList());
    }
}
