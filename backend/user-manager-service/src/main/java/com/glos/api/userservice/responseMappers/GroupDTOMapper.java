package com.glos.api.userservice.responseMappers;

import com.glos.api.userservice.entities.Group;
import com.glos.api.userservice.mappers.AbstractMapper;
import com.glos.api.userservice.responseDTO.GroupDTO;
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
    }

    @Override
    protected void postDtoCopy(Group source, GroupDTO destination) {
        destination.setOwner(userDTOMapper.toDto(source.getOwner()));
    }
}