package com.openclassroom.orion.module.user.controller;

import com.openclassroom.orion.module.user.DTO.RegisterRequest;
import com.openclassroom.orion.module.user.DTO.RegisterResponse;
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
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserDTO newUser = userService.registerNewUser(registerRequest);

            RegisterResponse response = new RegisterResponse();
            response.setUserId(newUser.getId());
            response.setUsername(newUser.getUsername());
            response.setMessage("Utilisateur créé avec succès");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            UserDTO newUser = userService.registerNewUser(registerRequest);

            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setUserId(newUser.getId());
            errorResponse.setUsername(newUser.getUsername());
            errorResponse.setMessage("Erreur lors de la création de l'utilisateur");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


}

