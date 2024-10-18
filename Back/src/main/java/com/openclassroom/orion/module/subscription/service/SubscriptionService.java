package com.openclassroom.orion.module.subscription.service;

import com.openclassroom.orion.auth.configuration.CustomUserDetails;
import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import com.openclassroom.orion.module.subscription.dto.SubscriptionGetByIdRequest;
import com.openclassroom.orion.module.subscription.dto.SubscriptionRequest;
import com.openclassroom.orion.module.subscription.dto.ThemeDTO;
import com.openclassroom.orion.module.subscription.model.Subscription;
import com.openclassroom.orion.module.subscription.model.SubscriptionId;
import com.openclassroom.orion.module.subscription.model.Theme;
import com.openclassroom.orion.module.subscription.repository.SubscriptionRepository;
import com.openclassroom.orion.module.subscription.repository.ThemeRepository;
import com.openclassroom.orion.module.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.openclassroom.orion.module.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SubscriptionService {

    private final ThemeRepository themeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(ThemeRepository themeRepository, SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    public List<ThemeDTO> getAllThemes() {
        // Récupère tous les thèmes à partir du repository
        List<Theme> themes = themeRepository.findAll();

        // Convertit les entités en DTOs
        return themes.stream()
                .map(this::convertToThemeDTO)
                    .collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getSubscriptionsByUser() {
        // Récupération de l'id
        Long userId = getCurrentUserId();
        // Cette ligne récupère une liste de tous les abonnements où l'user_id correspond à l'userId donné
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

        // Transforme la liste des abonnements en liste de thèmes
        return subscriptions.stream()
                .map(this::convertToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public void subscriptionTheme(SubscriptionRequest subscriptionRequest) {
        // Récupération de l'id
        Long userId = getCurrentUserId();

        // Vérifier si l'utilisateur existe
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("L'utilisateur avec l'id " + userId + " n'existe pas.");
        }

        // Vérifier si le thème existe
        Optional<Theme> themeOpt = themeRepository.findById(subscriptionRequest.getThemeId());
        if (themeOpt.isEmpty()) {
            throw new IllegalArgumentException("Le thème avec l'id " + subscriptionRequest.getThemeId() + " n'existe pas.");
        }

        // Construire la clé composite de l'abonnement
        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setUser(subscriptionRequest.getUserId());
        subscriptionId.setTheme(subscriptionRequest.getThemeId());

        // Vérifier si l'abonnement existe déjà
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(subscriptionId);
        if (existingSubscription.isPresent()) {
            throw new IllegalStateException("L'utilisateur est déjà abonné à ce thème.");
        }

        // Créer un nouvel abonnement avec les entités User et Theme
        Subscription newSubscription = new Subscription();
        newSubscription.setUser(userOpt.get()); // Définir l'utilisateur
        newSubscription.setTheme(themeOpt.get()); // Définir le thème
        subscriptionRepository.save(newSubscription); // Sauvegarder le nouvel abonnement
    }

    public void unsubscriptionTheme(SubscriptionRequest subscriptionRequest) {
        // Récupération de l'id
        Long userId = getCurrentUserId();

        // Construire la clé composite de l'abonnement pour la recherche
        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setUser(userId);
        subscriptionId.setTheme(subscriptionRequest.getThemeId());

        // Vérifier si l'abonnement existe
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isEmpty()) {
            throw new IllegalArgumentException("Aucun abonnement trouvé pour l'utilisateur avec l'id "
                    + subscriptionRequest.getUserId() + " et le thème " + subscriptionRequest.getThemeId() + ".");
        }

        // Supprimer l'abonnement trouvé
        subscriptionRepository.delete(subscriptionOpt.get());
    }
    private SubscriptionDTO convertToSubscriptionDTO(Subscription subscription) {
        // Crée et retourne un nouveau SubscriptionDTO à partir de l'entité Subscription
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscription.getTheme().getId());
        subscriptionDTO.setName(subscription.getTheme().getName());
        // et ajoute les autres champs nécessaires
        return subscriptionDTO;
    }

    private ThemeDTO convertToThemeDTO(Theme theme) {
        ThemeDTO dto = new ThemeDTO();
        dto.setId(theme.getId());
        dto.setName(theme.getName());
        dto.setDescription(theme.getDescription());
        return dto;
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

