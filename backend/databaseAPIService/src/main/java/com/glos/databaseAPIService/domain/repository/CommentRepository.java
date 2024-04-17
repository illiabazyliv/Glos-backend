package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Comment;
import com.glos.databaseAPIService.domain.filters.CommentFilter;
import com.glos.databaseAPIService.domain.filters.EntityFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{
    @Query(value = """
            SELECT comment FROM Comment comment
            WHERE :#{#filter.asMap().id} IS NULL OR comment.id = :#{#filter.asMap().id}
            AND :#{#filter.asMap().author} IS NULL OR comment.author = :#{#filter.asMap().author}
            AND :#{#filter.asMap().date} IS NULL OR comment.date = :#{#filter.asMap().date}
            """)
    List<Comment> findAllByFilter(@Param("filter") EntityFilter filter);
}
