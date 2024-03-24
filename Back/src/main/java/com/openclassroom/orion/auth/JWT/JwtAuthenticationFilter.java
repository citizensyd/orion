package com.openclassroom.orion.auth.JWT;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.openclassroom.orion.auth.DTO.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.orion.auth.JWT.JWTservice;
import com.openclassroom.orion.auth.configuration.CustomUserDetails;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final UserDetailsService userDetailsService;

    private final JWTservice jwtService;

    private final ObjectMapper objectMapper;


    /**
     * Extracts the JWT from the Authorization header.
     *
     * @param authHeader The Authorization header value.
     * @return The extracted JWT.
     * @throws IllegalArgumentException If the Authorization header is invalid or missing.
     */
    private String extractJwtFromHeader(String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            if (authHeader.length() > 7) {
                return authHeader.substring(7);
            } else {
                throw new IllegalArgumentException("Invalid Authorization header");
            }
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
    }


    /**
     * This method loads the user details based on the provided user email.
     *
     * @param userEmail The email of the user whose details are to be loaded.
     * @return The user details.
     */
    private CustomUserDetails loadUserDetails(String userEmail) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (!(userDetails instanceof CustomUserDetails)) {
            throw new UsernameNotFoundException("L'utilisateur avec l'email " + userEmail + " ne peut pas être converti en CustomUserDetails");
        }
        return (CustomUserDetails) userDetails;
    }


    /**
     * An array of whitelisted paths.
     * These paths are allowed to bypass certain authentication checks or filters.
     */
    private static final String[] WHITELISTED_PATHS = {"/api/auth/register", "/api/auth/login", "/swagger-ui/", "/v3/api-docs",};

    /**
     * Determines whether the given request should not be filtered.
     *
     * @param request The HTTP request.
     * @return True if the request URI starts with any of the whitelisted paths specified in the WHITELISTED_PATHS array, false otherwise.
     * @throws ServletException If an error occurs while processing the request.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(WHITELISTED_PATHS).anyMatch(request.getRequestURI()::startsWith);
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object The object to convert.
     * @return The JSON string representation of the object.
     * @throws IOException If an error occurs while converting the object to JSON.
     */
    private String convertObjectToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("Tentative d'authentification pour la requête: {}", request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                handleJwtError(response, HttpStatus.BAD_REQUEST, "Invalid or missing Authorization header: 'Bearer' prefix is required");
                return;
            }

            String jwt = extractJwtFromHeader(authHeader);
            logger.info("JWT extrait: {}", jwt);

            String userEmail = jwtService.extractEmail(jwt);

            if (userEmail == null) {
                handleJwtError(response, HttpStatus.UNAUTHORIZED, "Invalid token: Missing user email");
                return;
            }

            UserDetails userDetails = loadUserDetails(userEmail);
            logger.info("UserDetails chargé pour l'email {}: {}", userEmail, userDetails);

            if (!(userDetails instanceof CustomUserDetails)) {
                logger.error("Les UserDetails chargés ne sont pas une instance de CustomUserDetails.");
                handleJwtError(response, HttpStatus.UNAUTHORIZED, "Invalid token: User details not found");
                return;
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

            if (!jwtService.isTokenValid(jwt, customUserDetails)) {
                handleJwtError(response, HttpStatus.UNAUTHORIZED, "Invalid token: Token is not valid");
                return;
            }

// Crée un nouvel objet UsernamePasswordAuthenticationToken pour représenter l'authentification de l'utilisateur
// userDetails : les détails de l'utilisateur récupérés à partir du token JWT
// null : les informations de nom d'utilisateur et de mot de passe (non utilisées dans le contexte JWT)
// userDetails.getAuthorities() : les autorisations de l'utilisateur obtenues à partir des détails de l'utilisateur
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

// Configure les détails d'authentification à partir de la requête HTTP actuelle
// Ces détails peuvent inclure des informations telles que l'adresse IP de la demande ou les informations du navigateur
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

// Définit l'objet d'authentification nouvellement créé dans le contexte de sécurité de Spring
// Ceci authentifie effectivement l'utilisateur pour la session en cours
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (TokenExpiredException e) {
            logger.warn("Tentative de connexion avec un token expiré.");
            handleJwtError(response, HttpStatus.UNAUTHORIZED, "Token expired");
            return;
        } catch (MalformedJwtException e) {
            logger.warn("Tentative de connexion avec un token malformé.");

            handleJwtError(response, HttpStatus.UNAUTHORIZED, "Malformed token");
            return;
        } catch (Exception e) {
            logger.warn("Tentative de connexion avec un token invalid.");
            handleJwtError(response, HttpStatus.UNAUTHORIZED, "Invalid token");
            return;
        }
        filterChain.doFilter(request, response);
    }


    /**
     * Handles JWT errors by creating a custom error response and sending it to the client.
     *
     * @param response The HttpServletResponse object used to send the error response.
     * @param status The HTTP status code of the error.
     * @param message The error message to be displayed.
     * @throws IOException If an error occurs while writing the error response.
     */
    private void handleJwtError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        CustomErrorResponse errorResponse = new CustomErrorResponse(status, message);
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(convertObjectToJson(errorResponse));
    }


}
