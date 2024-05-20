package com.glos.commentservice.domain.DTO;

import com.glos.commentservice.domain.entities.Group;
import com.glos.commentservice.domain.entities.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO
{
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
