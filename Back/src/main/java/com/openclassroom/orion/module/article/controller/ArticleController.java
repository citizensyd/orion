package com.openclassroom.orion.module.article.controller;

import com.openclassroom.orion.module.article.dto.ArticleDTO;
import com.openclassroom.orion.module.article.dto.ArticleRequest;
import com.openclassroom.orion.module.article.exception.CustomDataAccessException;
import com.openclassroom.orion.module.article.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Récupérer le fil d'actualité
    @GetMapping("/feed")
    @Operation(summary = "Récupérer le fil d'actualité",
            description = "Obtient une liste des derniers articles pour le fil d'actualité.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès"),
                    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content())
            })
    public ResponseEntity<List<ArticleDTO>> getArticlesFeed() {
        try {
            List<ArticleDTO> articleDTOs = articleService.getArticlesFeed();
            return new ResponseEntity<>(articleDTOs, HttpStatus.OK);
        } catch (CustomDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ajouter un article
    @PostMapping
    @Operation(summary = "Ajouter un article",
            description = "Ajoute un nouvel article dans le système.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Article créé avec succès"),
                    @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content())
            })
    public ResponseEntity<ArticleDTO> addArticle(@RequestBody ArticleRequest articleRequest) {
        try {
            ArticleDTO newArticleDTO = articleService.addArticle(articleRequest);
            return new ResponseEntity<>(newArticleDTO, HttpStatus.CREATED);
        } catch (CustomDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Consulter un article par son ID
    @GetMapping("/{id}")
    @Operation(summary = "Consulter un article par son ID",
            description = "Récupère les détails d'un article en fonction de son ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Détails de l'article récupérés avec succès"),
                    @ApiResponse(responseCode = "404", description = "Article non trouvé", content = @Content())
            })
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

}


