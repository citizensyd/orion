package com.openclassroom.orion.module.subscription.controller;

import com.openclassroom.orion.module.subscription.dto.SubscriptionRequest;
import com.openclassroom.orion.module.subscription.model.Theme;
import com.openclassroom.orion.module.subscription.service.SubscriptionService;
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
    public ResponseEntity<String> subscribeToTheme(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.subscriptionTheme(subscriptionRequest);
        return ResponseEntity.ok("Abonnement réalisé avec succès.");
    }

    // Endpoint pour se désabonner
    @DeleteMapping
    public ResponseEntity<String> unsubscribeFromTheme(@RequestBody SubscriptionRequest subscriptionRequest) {
        subscriptionService.unsubscriptionTheme(subscriptionRequest);
        return ResponseEntity.ok("Désabonnement réalisé avec succès.");
    }
    // Endpoint pour obtenir la liste des thèmes auxquels un utilisateur est abonné
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Theme>> getSubscriptionsByUser(@PathVariable Long userId) {
        List<Theme> subscriptions = subscriptionService.getSubscriptionsByUser(userId);
        if (subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }
}
