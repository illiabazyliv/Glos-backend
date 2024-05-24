package com.glos.accessservice.responseMappers;

import com.glos.accessservice.entities.Repository;
import com.glos.accessservice.entities.mappers.AbstractMapper;
import com.glos.accessservice.responseDTO.RepositoryDTO;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDTOMapper extends AbstractMapper<Repository, RepositoryDTO>
{

    private final UserDTOMapper userDTOMapper;
    private final CommentDTOMapper commentDTOMapper;

    public RepositoryDTOMapper(UserDTOMapper userDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.userDTOMapper = userDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    @Override

    protected void postDtoCopy(Repository source, RepositoryDTO destination)
    {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
    }

    @Override
    protected void postEntityCopy(RepositoryDTO source, Repository destination)
    {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).toList());
    }
}
