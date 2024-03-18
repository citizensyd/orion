package com.openclassroom.orion.module.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO pour la mise à jour des informations de l'utilisateur")
public class UpdateRequest {

    @Schema(description = "L'ID unique de l'utilisateur", example = "1", required = true)
    private Long id;

    @Schema(description = "Le nom d'utilisateur, requis pour la mise à jour", example = "newjohndoe", required = false)
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
    private String username;

    @Schema(description = "L'adresse email de l'utilisateur, requise pour la mise à jour", example = "newjohndoe@example.com", required = false)
    @Email(message = "L'adresse email doit être valide.")
    private String email;

    @Schema(description = "Le mot de passe de l'utilisateur pour l'authentification ou la mise à jour du mot de passe. Laisser vide si non modifié", example = "NewMySecurePassword!123", required = false)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$", message = "Le mot de passe doit contenir entre 8 et 50 caractères, incluant au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial (@#$%^&+=).")
    private String password;
}
