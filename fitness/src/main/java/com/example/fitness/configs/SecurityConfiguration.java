package com.example.fitness.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors() // Enable CORS
                .and()
                .authorizeHttpRequests()
                // Public APIs that don't require authentication
                .requestMatchers("/users/all", "/auth/**", "/auth/login", "/category/**", "/cart/**", "/getproducts", "/images/**"
                        ,"/getproduct/**","/getproductbycategory/**","/users/adduser")
                .permitAll()
                // Protected APIs that require authentication
//                .requestMatchers()
//                .authenticated()
                // Default policy: all other endpoints require authentication
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Set allowed origins for CORS (React app URL)
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Update this to match your React app's URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow all necessary methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With")); // Allow essential headers
        configuration.setAllowCredentials(true); // Allow cookies and Authorization header

        // Apply CORS configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}