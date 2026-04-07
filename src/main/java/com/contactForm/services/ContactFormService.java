package com.contactForm.services;

import com.contactForm.models.ContactForm;
import com.contactForm.repositories.ContactFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactFormService {

    // Inyectamos el repositorio para poder usar la base de datos
    @Autowired
    private ContactFormRepository contactFormRepository;

    @Autowired
    private EmailService emailService;

    public ContactForm guardarMensaje(ContactForm mensaje) {
        // 1. Guardamos el mensaje en la base de datos (FreeDB)
        ContactForm mensajeGuardado = contactFormRepository.save(mensaje);

        // 2. Intentamos enviar el correo de notificación
        try {
            emailService.enviarNotificacion(mensajeGuardado);
        } catch (Exception e) {
            // Atrapamos el error para asegurar que la aplicación no colapse y el usuario reciba
            // su mensaje de "Éxito" en el frontend, ya que sí se guardó en la base de datos.
            System.err.println("Error al enviar el correo de notificación: " + e.getMessage());
        }

        return mensajeGuardado;
    }
}