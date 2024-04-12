package com.glos.databaseAPIService.domain.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "repositories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner_id", "root_full_name"}, name = "uq_repositories_owner_id_root_full_name"),
                @UniqueConstraint(columnNames = {"owner_id", "is_default"}, name = "uq_repositories_owner_id_is_default")
        }
)
public class Repository
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "root_path", nullable = false, length = 255)
    private String rootPath;

    @Column(name = "root_name", nullable = false, length = 255)
    private String rootName;

    @Column(name = "root_full_name", nullable = false, length = 255)
    private String rootFullName;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_repositories_users_id"))
    private User owner;

    @Column(name = "is_default", nullable = true, columnDefinition = "boolean default null")
    private boolean isDefault;

    @Column(name = "display_path", length = 255)
    private String displayPath;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Column(name = "display_full_name", length = 255)
    private String displayFullName;

    @Column(name = "description", length = 200)
    private String description;

    @ManyToMany
    @JoinTable(name = "groups_access_types", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "access_type_id"),
            foreignKey = @ForeignKey(name = "fk_groups_access_types_groups_id"),
            inverseForeignKey = @ForeignKey(name = "fk_groups_access_types_access_types_id"))
    private List<AccessType> accessTypes;

    @ManyToMany
    @JoinTable(name = "repositories_comments", joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"),
    foreignKey = @ForeignKey(name = "fk_repositories_comments_repositories_id"),
    inverseForeignKey = @ForeignKey(name = "fk_repositories_comments_comments_id"))
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "repositories_secure_codes", joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "secure_code_id"),
    foreignKey = @ForeignKey(name = "fk_repositories_secure_codes_repositories_id"),
    inverseForeignKey = @ForeignKey(name = "fk_repositories_secure_codes_secure_codes_id"))
    private List<SecureCode> secureCodes;

    @ManyToMany
    @JoinTable(name = "repositories_tags", joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
    foreignKey = @ForeignKey(name = "fk_repositories_tags_repositories_id"),
    inverseForeignKey = @ForeignKey(name = "fk_repositories_tags_tags_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "repository")
    private List<File> files;
}
