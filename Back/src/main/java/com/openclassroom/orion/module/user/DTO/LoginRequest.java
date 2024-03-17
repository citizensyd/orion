package com.openclassroom.orion.module.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(description = "Adresse email de l'utilisateur pour la connexion",
            example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur pour la connexion",
            example = "Password@123", required = true)
    private String password;
}

