package com.glos.commentservice.domain.responseMappers;
import com.glos.commentservice.domain.DTO.FileDTO;
import com.glos.commentservice.domain.requestFilters.FileRequestFilter;
import com.glos.commentservice.entities.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class FileRequestMapper extends AbstractMapper<FileDTO, FileRequestFilter>
{
    private final RepositoryDTOMapper repositoryDTOMapper;
    private final CommentDTOMapper commentDTOMapper;


    public FileRequestMapper(RepositoryDTOMapper repositoryDTOMapper, CommentDTOMapper commentDTOMapper) {
        this.repositoryDTOMapper = repositoryDTOMapper;
        this.commentDTOMapper = commentDTOMapper;
    }

    @Override
    protected void postDtoCopy(FileDTO source, FileRequestFilter destination)
    {
        destination.setRepository(source.getRepository());
        destination.setComments(source.getComments());
    }
    @Override
    protected void postEntityCopy(FileRequestFilter source, FileDTO destination)
    {
        destination.setRepository(source.getRepository());
        destination.setComments(source.getComments());
    }
}
