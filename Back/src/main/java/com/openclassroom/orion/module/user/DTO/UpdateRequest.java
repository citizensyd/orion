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


    @Schema(description = "Le nom d'utilisateur, requis pour la mise à jour", example = "newjohndoe", required = false)
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
    private String username;

    @Schema(description = "L'adresse email de l'utilisateur, requise pour la mise à jour", example = "newjohndoe@example.com", required = false)
    @Email(message = "L'adresse email doit être valide.")
    private String email;

}
