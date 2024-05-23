package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.databaseAPIService.domain.entities.Group;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import org.springframework.stereotype.Component;

@Component
public class GroupDTOMapper extends AbstractMapper<Group, GroupDTO> {
    private UserDTOMapper userDTOMapper;

    public GroupDTOMapper(UserDTOMapper userDTOMapper) {
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    protected void postEntityCopy(GroupDTO source, Group destination) {
        destination.setOwner(userDTOMapper.toEntity(source.getOwner()));
        if (source.getUsers() != null) {
            destination.setUsers(source.getUsers().stream().map(userDTOMapper::toEntity).toList());
        }
    }

    @Override
    protected void postDtoCopy(Group source, GroupDTO destination) {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
        if (source.getUsers() != null) {
            destination.setUsers(source.getUsers().stream().map(userDTOMapper::toDto).toList());
        }
    }
}
