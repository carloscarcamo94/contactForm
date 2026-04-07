package com.contactForm.controllers;

import com.contactForm.models.ContactForm;
import com.contactForm.services.ContactFormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/contacto")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping
    public ResponseEntity<String> recibirMensaje(@Valid @RequestBody ContactForm mensaje) {
        try {
            contactFormService.guardarMensaje(mensaje);
            return new ResponseEntity<>("¡Mensaje guardado con éxito!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el mensaje", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}