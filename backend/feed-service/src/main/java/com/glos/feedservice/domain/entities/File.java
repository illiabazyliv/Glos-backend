package com.glos.feedservice.domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.List;

@Entity
@Table(
        name = "files",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"repository_id", "root_full_name"}, name = "uq_repositories_repository_id_root_full_name")
        }
)
public class File
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "root_path", nullable = false, length = 255)
    private String rootPath;

    @Column(name = "root_filename", nullable = false, length = 255)
    private String rootFilename;

    @Column(name = "root_full_name", nullable = false, length = 255)
    private String rootFullName;

    @Column(name = "root_size", nullable = false)
    @Check(constraints = "root_size >= 0")
    private int rootSize;

    @Column(name = "root_format", nullable = false, length = 10)
    private String rootFormat;

    @Column(name = "display_path", length = 255)
    private String displayPath;

    @Column(name = "display_filename", length = 255)
    private String displayFilename;

    @Column(name = "display_full_name", length = 255)
    private String displayFullName;

    @ManyToOne
    @JoinColumn(name = "repository_id", nullable = false, foreignKey = @ForeignKey(name = "fk_files_repositories_id"))
    private Repository repository;

    @ManyToMany
    @JoinTable(name = "files_access_types", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "access_type_id"),
            foreignKey = @ForeignKey(name = "fk_files_access_types_files_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_access_types_access_types_id"))
    private List<AccessType> accessTypes;

    @ManyToMany
    @JoinTable(name = "files_comments", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"),
            foreignKey = @ForeignKey(name = "fk_files_comments_files_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_comments_comments_id"))
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "files_secure_codes", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "secure_code_id"),
            foreignKey = @ForeignKey(name = "fk_files_secure_codes_secure_codes_id"),
    inverseForeignKey = @ForeignKey(name = "fk_files_secure_codes_files_id"))
    private List<SecureCode> secureCodes;

    @ManyToMany
    @JoinTable(name = "files_tags", joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            foreignKey = @ForeignKey(name = "fk_files_tags_files_id"),
            inverseForeignKey = @ForeignKey(name = "fk_files_tags_tags_id")
    )
    private List<Tag> tags;

    public Long getId() {
        return id;
    }
}
