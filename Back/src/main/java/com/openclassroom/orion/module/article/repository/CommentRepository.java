package com.openclassroom.orion.module.article.repository;

import com.openclassroom.orion.module.article.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
