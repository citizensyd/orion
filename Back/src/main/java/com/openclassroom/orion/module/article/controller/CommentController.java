package com.openclassroom.orion.module.article.controller;

import com.openclassroom.orion.module.article.dto.CommentRequest;
import com.openclassroom.orion.module.article.dto.CommentResponse;
import com.openclassroom.orion.module.article.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "Obtenir les commentaires d'un article",
            description = "Récupère la liste des commentaires pour un article spécifique en utilisant l'ID de l'article.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Commentaires récupérés avec succès"),
                    @ApiResponse(responseCode = "404", description = "Article non trouvé")
            })
    public ResponseEntity<List<CommentResponse>> getCommentsByArticleId(@PathVariable Long articleId) {
        List<CommentResponse> commentResponses = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(commentResponses);
    }
    @PostMapping
    @Operation(summary = "Ajouter un commentaire à un article",
            description = "Permet d'ajouter un commentaire à un article spécifique en utilisant l'ID de l'article.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Commentaire créé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Informations fournies invalides"),
                    @ApiResponse(responseCode = "404", description = "Article non trouvé")
            })
    public ResponseEntity<CommentResponse> addCommentToArticle(@RequestBody CommentRequest commentRequest) {
        CommentResponse savedCommentResponse = commentService.addCommentToArticle(commentRequest);
        return new ResponseEntity<>(savedCommentResponse, HttpStatus.CREATED);
    }
}
