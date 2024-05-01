package com.glos.databaseAPIService.domain.responseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AccessTypeDTO
{
    private Long id;
    private String name;

    public AccessTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccessTypeDTO() {
    }

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
}
