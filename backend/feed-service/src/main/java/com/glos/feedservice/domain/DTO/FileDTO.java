package com.glos.feedservice.domain.DTO;

import com.glos.feedservice.domain.entities.*;
import com.glos.feedservice.domain.entities.AccessType;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

public class FileDTO
{
    private Long id;
    private int rootSize;

    private String rootFormat;

    private String displayPath;

    private String displayFilename;

    private String displayFullName;
    private List<AccessType> accessTypes;
    private List<Comment> comments;
    private List<Tag> tags;
}
