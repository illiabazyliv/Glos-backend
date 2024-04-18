package com.glos.feedservice.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "repositories_users_access_types"
)
public class RepositoryUserAccessType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "repository_id", foreignKey = @ForeignKey(name = "fk_repositories_users_access_types_repositories_id"))
    private Repository repository;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_repositories_users_access_types_users_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "access_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_repositories_users_access_types_types_id"))
    private AccessType accessType;

    public Long getId() {
        return id;
    }
}
