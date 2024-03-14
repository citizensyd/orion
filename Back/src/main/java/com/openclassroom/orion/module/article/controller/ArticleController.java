package com.openclassroom.orion.module.article.controller;

import com.openclassroom.orion.module.article.dto.ArticleDTO;
import com.openclassroom.orion.module.article.exception.CustomDataAccessException;
import com.openclassroom.orion.module.article.service.ArticleService;
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
    public ResponseEntity<ArticleDTO> addArticle(@RequestBody ArticleDTO articleDTO) {
        try {
            ArticleDTO newArticleDTO = articleService.addArticle(articleDTO);
            return new ResponseEntity<>(newArticleDTO, HttpStatus.CREATED);
        } catch (CustomDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Consulter un article par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

}


