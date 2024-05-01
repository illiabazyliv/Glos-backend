package com.glos.databaseAPIService.domain.responseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TagDTO
{
    private Long id;

    private String name;

    public TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDTO() {
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
