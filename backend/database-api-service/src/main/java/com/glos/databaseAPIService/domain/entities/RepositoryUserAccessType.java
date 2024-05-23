package com.glos.databaseAPIService.domain.entities;

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
    public void setId(Long id) {
        this.id = id;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public RepositoryUserAccessType(Long id, Repository repository, User user, AccessType accessType) {
        this.id = id;
        this.repository = repository;
        this.user = user;
        this.accessType = accessType;
    }

    public RepositoryUserAccessType() {
    }
}