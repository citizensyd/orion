package com.openclassroom.orion.module.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO pour la mise à jour des informations de l'utilisateur")
public class UpdateRequest {

    @Schema(description = "L'ID unique de l'utilisateur",
            example = "1",
            required = true)
    private Long id;

    @Schema(description = "Le nom d'utilisateur, requis pour la mise à jour",
            example = "newjohndoe",
            required = false)
    private String username;

    @Schema(description = "L'adresse email de l'utilisateur, requise pour la mise à jour",
            example = "newjohndoe@example.com",
            required = false)
    private String email;

    @Schema(description = "Le mot de passe de l'utilisateur pour l'authentification ou la mise à jour du mot de passe. Laisser vide si non modifié",
            example = "newMySecurePassword!",
            required = false) //
    private String password;

}
