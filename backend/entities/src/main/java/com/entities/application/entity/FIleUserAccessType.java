package com.entities.application.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "files_user_access_types"
)
public class FIleUserAccessType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false, foreignKey = @ForeignKey(name = "fk_files_user_access_types_files_id"))
    private File file;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_files_user_access_types_users_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "access_type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_files_user_access_types_access_types_id"))
    private AccessType accessType;
}
