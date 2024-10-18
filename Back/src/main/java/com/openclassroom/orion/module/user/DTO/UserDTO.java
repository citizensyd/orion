package com.openclassroom.orion.module.user.DTO;

import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO pour les informations de l'utilisateur")
public class UserDTO {

    @Schema(description = "L'ID unique de l'utilisateur", example = "3", required = true)
    private Long id;

    @Schema(description = "Le nom d'utilisateur", example = "johndoe", required = true)
    private String username;

    @Schema(description = "L'adresse email de l'utilisateur", example = "johndoe@example.com", required = true)
    private String email;

    @Schema(description = "La liste des abonnements de l'utilisateur")
    private List<SubscriptionDTO> subscriptions;

    @Schema(description = "Message de confirmation", example = "Création de compte réussi")
    private String message;
}
