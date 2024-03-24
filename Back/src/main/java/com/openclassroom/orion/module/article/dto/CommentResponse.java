package com.openclassroom.orion.module.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Représente un commentaire sur un article, incluant son contenu et les identifiants de l'article et de l'utilisateur qui a posté le commentaire")
public class CommentResponse {

    @Schema(description = "L'ID unique du commentaire", example = "10", required = false)
    private Long id;

    @Schema(description = "Le contenu du commentaire", example = "Cet article est très informatif, merci pour le partage !", required = true)
    private String content;

    @Schema(description = "L'ID de l'article auquel le commentaire est associé", example = "3", required = true)
    private Long articleId;

    @Schema(description = "L'ID de l'utilisateur qui a posté le commentaire", example = "3", required = true)
    private Long userId;
}

