package com.glos.groupservice.responseMappers;

import com.glos.api.entities.Group;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.dto.GroupDTO;
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