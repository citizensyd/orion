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
public class RegisterRequest {

    @Schema(description = "Nom d'utilisateur unique pour le login", example = "johndoe")
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
    private String username;

    @Schema(description = "Adresse email de l'utilisateur", example = "john.doe@example.com", required = true)
    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "L'email doit être valide.")
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "Password@123")
    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$", message = "Le mot de passe doit contenir entre 8 et 50 caractères, incluant au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial (@#$%^&+=).")
    private String password;
}
