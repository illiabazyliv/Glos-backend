package com.glos.api.entities;

import jakarta.persistence.*;


@Entity
@Table(
        name = "files_user_access_types"
)
public class FileUserAccessType
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
}
