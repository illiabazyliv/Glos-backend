package com.glos.groupservice.responseDTO;

import jakarta.persistence.Column;

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
