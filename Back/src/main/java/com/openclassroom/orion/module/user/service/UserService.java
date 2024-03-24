package com.openclassroom.orion.module.user.service;

import com.openclassroom.orion.auth.configuration.CustomUserDetails;
import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import com.openclassroom.orion.module.subscription.dto.SubscriptionGetByIdRequest;
import com.openclassroom.orion.module.subscription.service.SubscriptionService;
import com.openclassroom.orion.module.user.DTO.RegisterRequest;
import com.openclassroom.orion.module.user.DTO.UpdateRequest;
import com.openclassroom.orion.module.user.DTO.UserDTO;
import com.openclassroom.orion.module.user.model.User;
import com.openclassroom.orion.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SubscriptionService subscriptionService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
    }

    public UserDTO registerNewUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalStateException("Username already in use");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(encodedPassword);

        User savedUser = userRepository.save(newUser);

        return convertToUserDTORegister(savedUser);
    }


    public UserDTO getUserProfile() {
        Long currentUserId = getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + currentUserId));

        return convertToUserDTO(user);
    }

    public UserDTO updateUserProfile(UpdateRequest updateRequest) {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(updateRequest.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        if (updateRequest.getUsername() != null && !updateRequest.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(updateRequest.getUsername())) {
            throw new IllegalStateException("Username already in use");
        }

        user.setEmail(updateRequest.getEmail() != null ? updateRequest.getEmail() : user.getEmail());
        user.setUsername(updateRequest.getUsername() != null ? updateRequest.getUsername() : user.getUsername());

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return convertToUserDTO(updatedUser);
    }

    // Méthode pour obtenir l'ID de l'utilisateur connecté
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Aucun utilisateur connecté");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getId();
        } else {
            throw new IllegalStateException("Le principal actuel n'est pas une instance de CustomUserDetails");
        }
    }




    private UserDTO convertToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        SubscriptionGetByIdRequest subscriptionGetByIdRequest = new SubscriptionGetByIdRequest();
        subscriptionGetByIdRequest.setUserId(user.getId());
        List<SubscriptionDTO> subscriptionDTOs = subscriptionService.getSubscriptionsByUser();
        userDTO.setSubscriptions(subscriptionDTOs);

        return userDTO;
    }

    private UserDTO convertToUserDTORegister(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getActualUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }
}

