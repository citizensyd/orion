package com.openclassroom.orion.module.article.service;

import com.openclassroom.orion.module.article.dto.ArticleDTO;
import com.openclassroom.orion.module.article.dto.ArticleRequest;
import com.openclassroom.orion.module.article.exception.ArticleNotFoundException;
import com.openclassroom.orion.module.article.exception.CustomDataAccessException;
import com.openclassroom.orion.module.article.model.Article;
import com.openclassroom.orion.module.article.repository.ArticleRepository;
import com.openclassroom.orion.module.subscription.model.Theme;
import com.openclassroom.orion.module.subscription.repository.ThemeRepository;
import com.openclassroom.orion.module.user.model.User;
import com.openclassroom.orion.module.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, ThemeRepository themeRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    public List<ArticleDTO> getArticlesFeed() {
        try {
            return articleRepository.findAllByOrderByCreatedAtDesc().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomDataAccessException("Erreur lors de la récupération des articles.", e);
        }
    }

    public ArticleDTO addArticle(ArticleRequest articleRequest) {
        try {
            Article article = convertToEntity(articleRequest);
            Article savedArticle = articleRepository.save(article);
            return convertToDTO(savedArticle);
        } catch (Exception e) {
            throw new CustomDataAccessException("Erreur lors de l'ajout de l'article.", e);
        }
    }

    public ArticleDTO getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ArticleNotFoundException("Aucun article trouvé avec l'ID " + id));
    }


    // Méthodes utilitaires pour la conversion entre entités et DTO
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setThemeId(article.getTheme().getId());
        articleDTO.setUserName(article.getUser().getActualUsername());
        articleDTO.setCreatedAt(article.getCreatedAt());

        return articleDTO;
    }

    private Article convertToEntity(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());

        Theme theme = themeRepository.findById(articleRequest.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException("Thème non trouvé"));
        User user = userRepository.findById(articleRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        article.setTheme(theme);
        article.setUser(user);

        return article;
    }

}
