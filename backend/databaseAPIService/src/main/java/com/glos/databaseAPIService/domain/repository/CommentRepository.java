package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
