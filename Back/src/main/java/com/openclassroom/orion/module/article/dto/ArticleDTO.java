package com.openclassroom.orion.module.article.dto;

import lombok.Data;

@Data
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private Long themeId;
    private Long userId;

}
