package com.glos.feedservice.domain.entityMappers;

import com.glos.feedservice.domain.DTO.FileDTO;
import com.glos.feedservice.domain.entities.File;
import com.glos.feedservice.domain.mappers.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class FileDTOMapper extends AbstractMapper<File, FileDTO> {
}
