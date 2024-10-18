package com.openclassroom.orion.module.user.controller;

import com.openclassroom.orion.auth.JWT.JWTservice;
import com.openclassroom.orion.auth.configuration.CustomUserDetails;
import com.openclassroom.orion.module.user.DTO.AuthResponse;
import com.openclassroom.orion.module.user.DTO.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Le contrôleur AuthController gère les requêtes d'authentification pour l'application.
 * Il fournit des opérations pour se connecter et obtenir un jeton JWT pour l'authentification des utilisateurs.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTservice jswService;

    /**
     * Construit un AuthController avec les objets nécessaires pour l'authentification.
     *
     * @param authenticationManager le gestionnaire d'authentification pour authentifier les requêtes de connexion.
     * @param jswService le service pour générer des jetons JWT après une authentification réussie.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTservice jswService) {
        this.authenticationManager = authenticationManager;
        this.jswService = jswService;
    }

    /**
     * Authentifie l'utilisateur à l'aide des identifiants fournis et retourne un jeton JWT en cas de succès.
     *
     * @param loginRequest les données de connexion de l'utilisateur contenant l'email et le mot de passe.
     * @return une réponse contenant le jeton JWT ou un message d'erreur en cas d'échec.
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur",
            description = "Cette opération permet à l'utilisateur de se connecter en validant ses identifiants et en retournant un jeton JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Connexion réussie, jeton JWT retourné", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Données de requête invalides", content = @Content())})
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jswService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Erreur d'authentification : " + e.getMessage()));
        }
    }
}

