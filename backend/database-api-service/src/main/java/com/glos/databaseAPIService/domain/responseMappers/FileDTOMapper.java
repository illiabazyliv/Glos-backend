package com.glos.databaseAPIService.domain.responseMappers;

import com.glos.api.entities.File;
import com.glos.databaseAPIService.domain.mappers.AbstractMapper;
import com.glos.databaseAPIService.domain.responseDTO.FileDTO;
import org.springframework.stereotype.Component;

@Component
public class FileDTOMapper extends AbstractMapper<File, FileDTO> {
}
