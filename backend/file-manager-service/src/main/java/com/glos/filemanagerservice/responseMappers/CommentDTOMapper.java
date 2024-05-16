package com.glos.filemanagerservice.responseMappers;

import com.glos.api.entities.Comment;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentDTOMapper extends AbstractMapper<CommentDTO, Comment>
{
    private final UserDTOMapper userMapper;

    public CommentDTOMapper(UserDTOMapper userMapper) {
        this.userMapper = userMapper;
    }

    protected void postEntityCopy(CommentDTO source, Comment destination)
    {
        destination.setAuthor(userMapper.toEntity(source.getAuthor()));
    }

    protected void postDtoCopy(Comment source, CommentDTO destination)
    {
        destination.setAuthor(userMapper.toDto(source.getAuthor()));
    }
}
