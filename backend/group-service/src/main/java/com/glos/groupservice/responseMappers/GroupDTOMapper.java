package com.glos.groupservice.responseMappers;

import com.glos.api.entities.Group;
import com.glos.groupservice.mappers.AbstractMapper;
import com.glos.groupservice.responseDTO.GroupDTO;
import org.springframework.stereotype.Component;

@Component
public class GroupDTOMapper extends AbstractMapper<Group, GroupDTO> {
}