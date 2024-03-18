package com.openclassroom.orion.module.user.controller;

import com.openclassroom.orion.auth.JWT.JWTservice;
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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTservice jswService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTservice jswService) {
        this.authenticationManager = authenticationManager;
        this.jswService = jswService;
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur",
            description = "Cette opération permet à l'utilisateur de se connecter en validant ses identifiants et en retournant un jeton JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Connexion réussie, jeton JWT retourné", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Erreur d'authentification", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Données de requête invalides", content = @Content())})
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jswService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur d'authentification : " + e.getMessage());
        }
    }
}

