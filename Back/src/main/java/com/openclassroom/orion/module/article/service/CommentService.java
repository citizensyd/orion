package com.openclassroom.orion.module.article.service;

import com.openclassroom.orion.auth.configuration.CustomUserDetails;
import com.openclassroom.orion.module.article.dto.CommentRequest;
import com.openclassroom.orion.module.article.dto.CommentResponse;
import com.openclassroom.orion.module.article.exception.ArticleNotFoundException;
import com.openclassroom.orion.module.article.model.Article;
import com.openclassroom.orion.module.article.model.Comment;
import com.openclassroom.orion.module.article.repository.ArticleRepository;
import com.openclassroom.orion.module.article.repository.CommentRepository;
import com.openclassroom.orion.module.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

// Ajouter un commentaire à un article en utilisant CommentDTO
    public CommentResponse addCommentToArticle(CommentRequest commentRequest) {
        Long currentUserId = getCurrentUserId();

        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article non trouvé"));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setArticle(article);
        comment.setUser(userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    // Méthode pour récupérer les commentaires d'un article par son ID
    public List<CommentResponse> getCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article non trouvé avec l'ID: " + articleId));

        List<Comment> comments = commentRepository.findAllByArticle(article);
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Modifier un commentaire caractéristique non implémenter
    public Comment updateComment(Long commentId, Comment updatedComment) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        comment.setContent(updatedComment.getContent());

        return commentRepository.save(comment);
    }

    // Supprimer un commentaire caractéristique non implémenter
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        commentRepository.deleteById(commentId);
    }

    // Conversion d'une entité Comment en CommentDTO
    private CommentResponse convertToDTO(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setUserName(comment.getUser().getActualUsername());
        commentResponse.setContent(comment.getContent());
        commentResponse.setArticleId(comment.getArticle().getId());
        commentResponse.setUserId(comment.getUser().getId());
        return commentResponse;
    }

    // Méthode pour obtenir l'ID de l'utilisateur connecté
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Aucun utilisateur connecté");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId();
        } else {
            throw new IllegalStateException("Le principal actuel n'est pas une instance de CustomUserDetails");
        }
    }
}
