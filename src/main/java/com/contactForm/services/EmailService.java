package com.contactForm.services;

import com.contactForm.models.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarNotificacion(ContactForm contacto) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        // El correo al que quieres que te lleguen las notificaciones
        message.setTo("carlos.carcamosegovia@gmail.com"); 
        
        message.setSubject("Nuevo correo en el buzón");
        message.setText("Alguien te está intentando contactar.\n\n" +
                "Detalles del contacto:\n" +
                "----------------------------------------------------------------------\n" +
                "Nombre: " + contacto.getContactName() + "\n" +
                "Teléfono: " + contacto.getContactPhone() + "\n" +
                "Correo: " + contacto.getContactEmail() + "\n" +
                "----------------------------------------------------------------------\n\n" +
                "Mensaje:\n" + contacto.getContactMessage());
        
        mailSender.send(message);
    }
}