package com.glos.commentservice.entities;

import java.time.LocalDateTime;


public class Comment
{
    private Long id;
    private User author;

    private String text;

    private LocalDateTime date;

    public Comment(Long id, User author, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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
