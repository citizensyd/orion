package com.openclassroom.orion.module.subscription.controller;

import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import com.openclassroom.orion.module.subscription.dto.SubscriptionGetByIdRequest;
import com.openclassroom.orion.module.subscription.dto.SubscriptionRequest;
import com.openclassroom.orion.module.subscription.service.SubscriptionService;
import com.openclassroom.orion.module.user.repository.UserRepository;
import com.openclassroom.orion.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;


    public SubscriptionController(SubscriptionService subscriptionService,  UserRepository userRepository) {
        this.subscriptionService = subscriptionService;
        this.userRepository = userRepository;
    }

    // Endpoint pour s'abonner
    @PostMapping
    @Operation(summary = "S'abonner à un thème",
            description = "Permet à un utilisateur de s'abonner à un thème spécifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Abonnement réalisé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content()),
                    @ApiResponse(responseCode = "409", description = "L'utilisateur est déjà abonné à ce thème.")
            })
    public ResponseEntity<String> subscribeToTheme(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.subscriptionTheme(subscriptionRequest);
        return ResponseEntity.ok("Abonnement réalisé avec succès.");
    }

    // Endpoint pour se désabonner
    @DeleteMapping
    @Operation(summary = "Se désabonner d'un thème",
            description = "Permet à un utilisateur de se désabonner d'un thème spécifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Désabonnement réalisé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content()),
            })
    public ResponseEntity<String> unsubscribeFromTheme(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.unsubscriptionTheme(subscriptionRequest);
        return ResponseEntity.ok("Désabonnement réalisé avec succès.");
    }

    // Endpoint pour obtenir la liste des thèmes auxquels un utilisateur est abonné
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtenir les abonnements d'un utilisateur",
            description = "Retourne la liste des thèmes auxquels un utilisateur est abonné en fonction de son ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des abonnements obtenue avec succès", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionDTO.class))}),
                    @ApiResponse(responseCode = "204", description = "Aucun contenu, l'utilisateur n'est abonné à aucun thème"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide, ID utilisateur incorrect"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            })
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUser(@PathVariable("userId") Long userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        if ( !userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        SubscriptionGetByIdRequest request = new SubscriptionGetByIdRequest();
        request.setUserId(userId);

        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByUser(request);
        if (subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }


}
