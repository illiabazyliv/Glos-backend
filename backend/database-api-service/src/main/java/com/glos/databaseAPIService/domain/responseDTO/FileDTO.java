package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.api.entities.*;
import com.glos.api.entities.AccessType;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

public class FileDTO
{
    private Long id;

    private String rootPath;

    private String rootFilename;

    private String rootFullName;

    private Integer rootSize;

    private String rootFormat;

    private String displayPath;

    private String displayFilename;

    private String displayFullName;

    private Repository repository;

    private List<AccessType> accessTypes;

    private List<Comment> comments;
    private List<SecureCode> secureCodes;
    private List<Tag> tags;
}
