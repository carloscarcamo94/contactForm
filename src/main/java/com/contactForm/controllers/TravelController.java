package com.contactForm.controllers;

import com.contactForm.dto.DestinoDTO;
import com.contactForm.services.TravelBucketListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
public class TravelController {

    @Autowired
    private TravelBucketListService travelService;

    @GetMapping("/destinos")
    public ResponseEntity<List<DestinoDTO>> getViajes() {
        List<DestinoDTO> viajes = travelService.obtenerDestinos();
        
        if (viajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(viajes);
    }
}