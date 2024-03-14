package com.openclassroom.orion.module.article.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;
    private String content;
    private Long articleId;
    private Long userId;

}
