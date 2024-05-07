package com.glos.commentservice.domain.DTO;

import com.glos.commentservice.domain.entities.Group;
import com.glos.commentservice.domain.entities.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO
{
    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
