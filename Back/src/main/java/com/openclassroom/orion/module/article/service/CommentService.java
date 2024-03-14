package com.openclassroom.orion.module.article.service;

import com.openclassroom.orion.module.article.dto.CommentDTO;
import com.openclassroom.orion.module.article.exception.ArticleNotFoundException;
import com.openclassroom.orion.module.article.model.Article;
import com.openclassroom.orion.module.article.model.Comment;
import com.openclassroom.orion.module.article.repository.ArticleRepository;
import com.openclassroom.orion.module.article.repository.CommentRepository;
import com.openclassroom.orion.module.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public CommentDTO addCommentToArticle(Long articleId, CommentDTO commentDTO) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article non trouvé"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setArticle(article);
        comment.setUser(userRepository.findById(commentDTO.getUserId()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
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
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setUserId(comment.getUser().getId());
        return commentDTO;
    }
}
