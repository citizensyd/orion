package com.openclassroom.orion.module.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

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

    @Schema(description = "Le nom du thème de l'article", example = "IA", required = true)
    private String theme;

    @Schema(description = "Le nom de l'utilisateur qui a posté l'article", example = "John Doe", required = true)
    private String userName;

    @Schema(description = "La date à laquelle l'article a été posté", example = "22/05/1984", required = true)
    private Date createdAt;
}

