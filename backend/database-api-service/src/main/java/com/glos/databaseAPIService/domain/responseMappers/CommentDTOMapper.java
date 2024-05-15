package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.api.entities.Comment;
import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.CommentDTO;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOMapper extends AbstractMapper<Comment, CommentDTO>
{
    private UserDTOMapper userDTOMapper;

    public CommentDTOMapper(UserDTOMapper userDTOMapper) {
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    protected void postEntityCopy(CommentDTO source, Comment destination)
    {
        destination.setAuthor(userDTOMapper.toEntity(source.getAuthor()));
    }

    @Override
    protected void postDtoCopy(Comment source, CommentDTO destination)
    {
        destination.setAuthor(userDTOMapper.toDto(source.getAuthor()));
    }

}
