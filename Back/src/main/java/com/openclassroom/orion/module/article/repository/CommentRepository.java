package com.openclassroom.orion.module.article.repository;

import com.openclassroom.orion.module.article.model.Article;
import com.openclassroom.orion.module.article.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticle(Article article);
}
