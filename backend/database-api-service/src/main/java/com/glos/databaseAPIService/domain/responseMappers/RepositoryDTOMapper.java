package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.api.entities.Repository;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.RepositoryDTO;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDTOMapper extends AbstractMapper<Repository, RepositoryDTO> {
}
