package com.contactForm.controllers;

import com.contactForm.dto.LibroDTO;
import com.contactForm.services.ReadingTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class NotionController {

    @Autowired
    private ReadingTrackerService readingTrackerService;

    @GetMapping("/actuales")
    public ResponseEntity<List<LibroDTO>> getLibrosActuales() {
        List<LibroDTO> libros = readingTrackerService.obtenerLibrosEnLectura();
        
        if (libros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(libros);
    }
}