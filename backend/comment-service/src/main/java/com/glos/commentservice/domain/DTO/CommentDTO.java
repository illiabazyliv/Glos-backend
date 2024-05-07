package com.glos.commentservice.domain.DTO;

import com.glos.commentservice.domain.entities.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class CommentDTO
{
    private Long id;
    private UserDTO author;

    private String text;

    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CommentDTO(Long id, UserDTO author, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
    }
}
