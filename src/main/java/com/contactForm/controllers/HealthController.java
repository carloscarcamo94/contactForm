package com.contactForm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        try {
            // Ejecutamos una consulta ultraligera para mantener el servidor de Rendere despierto
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            
            response.put("status", "ok");
            response.put("message", "El servidor de Render y la base de datos Supabase están activas.");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error conectando a la base de datos.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}