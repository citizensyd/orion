package com.openclassroom.orion.module.article.controller;

import com.openclassroom.orion.module.article.dto.CommentDTO;
import com.openclassroom.orion.module.article.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addCommentToArticle(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        CommentDTO savedCommentDTO = commentService.addCommentToArticle(articleId, commentDTO);
        return new ResponseEntity<>(savedCommentDTO, HttpStatus.CREATED);
    }

}

