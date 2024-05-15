package com.glos.databaseAPIService.domain.responseDTO;

import com.glos.api.entities.User;

import java.time.LocalDateTime;

public class CommentDTO
{
    private Long id;
    private UserDTO author;
    private String text;
    private LocalDateTime date;

    public CommentDTO() {
    }

    public CommentDTO(Long id, UserDTO author, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
    }

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
}
