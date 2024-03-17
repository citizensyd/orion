package com.openclassroom.orion.module.user.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    @Schema(description = "Nom d'utilisateur unique pour le login", example = "johndoe")
    private String username;

    @Schema(description = "Adresse email de l'utilisateur", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Mot de passe de l'utilisateur", example = "Password@123")
    private String password;


}
