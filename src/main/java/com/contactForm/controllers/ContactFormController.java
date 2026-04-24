package com.contactForm.controllers;

import com.contactForm.models.ContactForm;
import com.contactForm.services.ContactFormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
    "https://carlosdev-portafolio.vercel.app/", 
    "http://localhost:5500", 
    "http://localhost:3000"
}) 
@RestController
@RequestMapping("/api/contacto")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping
    public ResponseEntity<String> recibirMensaje(@Valid @RequestBody ContactForm mensaje) {
        // El GlobalExceptionHandler interceptará la falla automáticamente si los datos son incorrectos
    	
        contactFormService.guardarMensaje(mensaje);
        return new ResponseEntity<>("¡Mensaje guardado con éxito!", HttpStatus.CREATED);
    }
}