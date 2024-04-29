package com.glos.api.entities;

import jakarta.persistence.*;

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
}
