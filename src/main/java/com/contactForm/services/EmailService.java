package com.contactForm.services;

import com.contactForm.models.ContactForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarNotificacion(ContactForm contacto) {
        String url = "https://api.resend.com/emails";

        // Configurar Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Configurar el cuerpo del mensaje (JSON)
        Map<String, Object> body = new HashMap<>();
        body.put("from", "onboarding@resend.dev"); // O tu dominio verificado
        body.put("to", "carlos.carcamosegovia@gmail.com");
        body.put("subject", "Nuevo contacto: " + contacto.getContactName());
        body.put("html", String.format(
            "<strong>Nombre:</strong> %s<br><strong>Teléfono:</strong> %s<br><strong>Email:</strong> %s<br><br><strong>Mensaje:</strong><p>%s</p>",
            contacto.getContactName(), contacto.getContactPhone(), contacto.getContactEmail(), contacto.getContactMessage()
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            System.err.println("Error al enviar con Resend: " + e.getMessage());
        }
    }
}