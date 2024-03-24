package com.openclassroom.orion.auth.configuration;

import com.openclassroom.orion.module.user.model.User;
import com.openclassroom.orion.module.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email non trouvé : " + email));

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getUsername(), // Assurez-vous que cela devrait être le nom d'utilisateur et non l'email, sinon changez en conséquence
                user.getPassword()
        );
    }

}
