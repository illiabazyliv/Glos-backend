package com.glos.databaseAPIService.domain.repository;

import com.glos.databaseAPIService.domain.entity.Comment;
import com.glos.databaseAPIService.domain.filters.CommentFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 	@author - yablonovskydima
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>
{
    @Query(value = """
            SELECT comment FROM Comment comment
            WHERE :#{#filter.id} IS NULL OR comment.id = :#{#filter.id}
            AND :#{#filter.author} IS NULL OR comment.author = :#{#filter.author}
            AND :#{#filter.date} IS NULL OR comment.date = :#{#filter.date}
            """)
    List<Comment> findAllByFilter(@Param("filter")CommentFilter filter);
}
