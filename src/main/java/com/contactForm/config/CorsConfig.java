package com.contactForm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir credenciales y encabezados
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.setAllowedOrigins(Arrays.asList(
        	"https://carloscarcamo.tech",
            "https://www.carloscarcamo.tech",
        	"https://carlosdev-portafolio.vercel.app", 
        	"http://localhost:5500", 
        	"http://localhost:3000"
        ));
        
        // Aplicamos esta configuración a todas las rutas de la API
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}