package com.openclassroom.orion.module.user.controller;

import com.openclassroom.orion.module.user.DTO.UpdateRequest;
import com.openclassroom.orion.module.user.DTO.UserDTO;
import com.openclassroom.orion.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Obtenir le profil d'un utilisateur",
            description = "Récupère le profil d'utilisateur par son ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profil d'utilisateur récupéré avec succès"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content())
            })
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Mettre à jour le profil d'un utilisateur",
            description = "Mise à jour des informations d'un utilisateur par son ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profil d'utilisateur mis à jour avec succès"),
                    @ApiResponse(responseCode = "400", description = "Données de la requête invalides", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content())
            })
    public ResponseEntity<UserDTO> updateUser(@Valid @PathVariable Long userId, @RequestBody UpdateRequest updateRequest) {
        UserDTO updatedUserDTO = userService.updateUserProfile(userId, updateRequest);
        return ResponseEntity.ok(updatedUserDTO);
    }

}
