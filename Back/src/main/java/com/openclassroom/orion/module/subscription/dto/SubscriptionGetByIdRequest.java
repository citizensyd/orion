package com.openclassroom.orion.module.subscription.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubscriptionGetByIdRequest {
    @Schema(description = "Identifiant unique de l'utilisateur",
            example = "3",
            required = true)
    @NotNull(message = "L'ID de l'utilisateur ne peut pas être nul.")
    @Positive(message = "L'ID de l'utilisateur doit être positif.")
    private Long userId;
}
