package com.glos.commentservice.domain.DTO;

import com.glos.commentservice.domain.entities.Group;
import com.glos.commentservice.domain.entities.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO
{
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO(Long id) {
        this.id = id;
    }
}
