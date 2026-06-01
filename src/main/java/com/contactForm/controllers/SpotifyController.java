package com.contactForm.controllers;

import com.contactForm.dto.CancionDTO;
import com.contactForm.services.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/actual")
    public ResponseEntity<CancionDTO> getCancionActual() {
        CancionDTO cancion = spotifyService.obtenerTrackActual();
        
        if (cancion == null) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(cancion);
    }
}