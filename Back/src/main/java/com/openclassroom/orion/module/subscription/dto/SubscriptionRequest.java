package com.openclassroom.orion.module.subscription.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubscriptionRequest {

    @NotNull(message = "L'ID de l'utilisateur ne peut pas être nul.")
    @Positive(message = "L'ID de l'utilisateur doit être positif.")
    private Long userId;

    @NotNull(message = "L'ID du thème ne peut pas être nul.")
    @Positive(message = "L'ID du thème doit être positif.")
    private Long themeId;
}
