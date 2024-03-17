package com.openclassroom.orion.module.subscription.controller;

import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import com.openclassroom.orion.module.subscription.dto.SubscriptionRequest;
import com.openclassroom.orion.module.subscription.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    // Endpoint pour s'abonner
    @PostMapping
    @Operation(summary = "S'abonner à un thème",
            description = "Permet à un utilisateur de s'abonner à un thème spécifique.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Abonnement réalisé avec succès"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content()),
                    // ... autres réponses potentielles ...
            })
    public ResponseEntity<String> subscribeToTheme(@RequestBody SubscriptionRequest subscriptionRequest) {
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
    public ResponseEntity<String> unsubscribeFromTheme(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.unsubscriptionTheme(subscriptionRequest);
        return ResponseEntity.ok("Désabonnement réalisé avec succès.");
    }

    // Endpoint pour obtenir la liste des thèmes auxquels un utilisateur est abonné
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtenir les abonnements d'un utilisateur",
            description = "Retourne la liste des thèmes auxquels un utilisateur est abonné.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des abonnements obtenue avec succès"),
                    @ApiResponse(responseCode = "204", description = "Aucun contenu, l'utilisateur n'est abonné à aucun thème"),
                    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content()),
            })
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByUser(@PathVariable Long userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByUser(userId);
        if (subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }
}
