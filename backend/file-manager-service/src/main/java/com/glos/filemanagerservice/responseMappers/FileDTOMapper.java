package com.glos.filemanagerservice.responseMappers;

import com.glos.api.entities.Comment;
import com.glos.api.entities.File;
import com.glos.filemanagerservice.DTO.CommentDTO;
import com.glos.filemanagerservice.DTO.FileDTO;
import com.glos.filemanagerservice.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class FileDTOMapper extends AbstractMapper<FileDTO, File>
{
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final CommentDTOMapper commentDTOMapper;


    public FileDTOMapper(RepositoryDTOMapper repositoryDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    protected void postEntityCopy(FileDTO source, File destination)
    {
        destination.setRepository(repositoryDTOMapper.toDto(source.getRepository()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toDto).toList());
    }

    protected void postDtoCopy(File source, FileDTO destination)
    {
        destination.setRepository(repositoryDTOMapper.toEntity(source.getRepository()));
        destination.setComments(source.getComments().stream().map(commentDTOMapper::toEntity).toList());
    }
}
