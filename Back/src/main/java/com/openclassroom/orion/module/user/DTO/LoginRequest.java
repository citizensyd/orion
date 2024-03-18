package com.openclassroom.orion.module.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(description = "Adresse email de l'utilisateur pour la connexion",
            example = "john.doe@example.com", required = true)
    @NotBlank(message = "L'adresse email ne peut pas être vide.")
    @Email(message = "L'adresse email doit être valide.")
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur pour la connexion",
            example = "Password@123", required = true)
    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    private String password;
}
