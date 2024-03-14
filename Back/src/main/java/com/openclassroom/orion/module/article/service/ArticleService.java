package com.openclassroom.orion.module.article.service;

import com.openclassroom.orion.module.article.dto.ArticleDTO;
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
            // Convertir les articles en DTO avant de les retourner
            return articleRepository.findAllByOrderByCreatedAtDesc().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomDataAccessException("Erreur lors de la récupération des articles.", e);
        }
    }

    public ArticleDTO addArticle(ArticleDTO articleDTO) {
        try {
            Article article = convertToEntity(articleDTO);
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
        articleDTO.setUserId(article.getUser().getId());
        return articleDTO;
    }

    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());

        Theme theme = themeRepository.findById(articleDTO.getThemeId())
                .orElseThrow(() -> new EntityNotFoundException("Thème non trouvé"));
        User user = userRepository.findById(articleDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        article.setTheme(theme);
        article.setUser(user);

        return article;
    }

}
