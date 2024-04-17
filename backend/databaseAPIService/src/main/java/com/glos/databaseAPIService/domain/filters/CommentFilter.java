package com.glos.databaseAPIService.domain.filters;

import com.glos.databaseAPIService.domain.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

public class CommentFilter implements EntityFilter
{
    private Long id;

    private User author;

    private LocalDateTime date;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public Map<String, Object> asMap() {
        return Map.of(
                "id",  id,
                "author", author,
                "date", date
        );
    }
}
