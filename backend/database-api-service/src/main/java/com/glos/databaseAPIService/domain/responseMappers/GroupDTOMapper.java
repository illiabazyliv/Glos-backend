package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.api.entities.Group;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.GroupDTO;
import org.springframework.stereotype.Component;

@Component
public class GroupDTOMapper extends AbstractMapper<Group, GroupDTO> {
}
