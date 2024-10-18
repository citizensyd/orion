package com.openclassroom.orion.module.subscription.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Requête pour s'abonner ou se désabonner d'un thème")
public class SubscriptionRequest {

    @Schema(description = "Identifiant unique de l'utilisateur",
            example = "3",
            required = true)
    @NotNull(message = "L'ID de l'utilisateur ne peut pas être nul.")
    @Positive(message = "L'ID de l'utilisateur doit être positif.")
    private Long userId;

    @Schema(description = "Identifiant unique du thème auquel s'abonner ou se désabonner",
            example = "1",
            required = true)
    @NotNull(message = "L'ID du thème ne peut pas être nul.")
    @Positive(message = "L'ID du thème doit être positif.")
    private Long themeId;
}
