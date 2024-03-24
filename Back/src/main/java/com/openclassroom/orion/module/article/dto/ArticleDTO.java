package com.openclassroom.orion.module.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Représente un article, incluant son titre, contenu, et les identifiants de son thème et de son auteur")
public class ArticleDTO {

    @Schema(description = "L'ID unique de l'article", example = "1", required = true)
    private Long id;

    @Schema(description = "Le titre de l'article", example = "Découverte scientifique majeure", required = true)
    private String title;

    @Schema(description = "Le contenu de l'article", example = "Aujourd'hui, une avancée majeure a été réalisée dans le domaine de la science...", required = true)
    private String content;

    @Schema(description = "L'ID du thème auquel l'article est associé", example = "3", required = true)
    private Long themeId;

    @Schema(description = "L'ID de l'utilisateur qui a posté l'article", example = "3", required = true)
    private Long userId;
}

