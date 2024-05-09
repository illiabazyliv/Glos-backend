package com.glos.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "`groups`",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"}, name = "uq_groups_name_owner_id")}
)
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",  nullable = false)
    private Long id;

    @Column(name = "`name`", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_groups_users_id"))
    private User owner;

    @ManyToMany
    @JoinTable(name = "groups_users", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "fk_groups_users_groups_id"),
            inverseForeignKey = @ForeignKey(name = "fk_groups_users_users_id"))
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "groups_access_types", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "access_type_id"),
            foreignKey = @ForeignKey(name = "fk_groups_access_types_groups_id"),
            inverseForeignKey = @ForeignKey(name = "fk_groups_access_types_access_types_id"))
    private List<AccessType> accessTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<AccessType> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public Group()
    {
        this.users = new ArrayList<>();
        this.accessTypes = new ArrayList<>();
    }

    public Group(Long id,
                 String name,
                 User owner,
                 List<User> users,
                 List<AccessType> accessTypes) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
        this.accessTypes = accessTypes;
    }
}
