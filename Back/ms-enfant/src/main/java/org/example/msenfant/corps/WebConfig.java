package org.example.msenfant.corps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Autoriser les requêtes depuis ton frontend Angular
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Remplace par l'URL de ton frontend si nécessaire
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Les méthodes HTTP autorisées
                .allowedHeaders("*"); // Tous les en-têtes sont autorisés
    }
}