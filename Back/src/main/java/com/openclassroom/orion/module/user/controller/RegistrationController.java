package com.openclassroom.orion.module.user.controller;

import com.openclassroom.orion.module.user.DTO.RegisterRequest;
import com.openclassroom.orion.module.user.DTO.UserDTO;
import com.openclassroom.orion.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Inscription d'un nouvel utilisateur",
            description = "Cette opération permet de créer un nouveau compte utilisateur.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Données de requête invalides ou problème lors de l'inscription", content = @Content()),
            })
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserDTO newUser = userService.registerNewUser(registerRequest);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

