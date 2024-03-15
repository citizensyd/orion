package com.openclassroom.orion.module.user.service;

import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import com.openclassroom.orion.module.subscription.service.SubscriptionService;
import com.openclassroom.orion.module.user.DTO.UserDTO;
import com.openclassroom.orion.module.user.model.User;
import com.openclassroom.orion.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserDTO registerNewUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalStateException("Username already in use");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(encodedPassword);

        User savedUser = userRepository.save(newUser);

        return convertToUserDTO(savedUser);
    }


    public UserDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        return convertToUserDTO(user);
    }

    public UserDTO updateUserProfile(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        if (userDTO.getUsername() != null && !userDTO.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalStateException("Username already in use");
        }

        user.setEmail(userDTO.getEmail() != null ? userDTO.getEmail() : user.getEmail());
        user.setUsername(userDTO.getUsername() != null ? userDTO.getUsername() : user.getUsername());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return convertToUserDTO(updatedUser);
    }


    private UserDTO convertToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        List<SubscriptionDTO> subscriptionDTOs = subscriptionService.getSubscriptionsByUser(user.getId());
        userDTO.setSubscriptions(subscriptionDTOs);

        return userDTO;
    }
}

