package com.contactForm.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    // Guardamos un cubo por cada dirección IP
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // Definimos las reglas, solamente aceptaremos 3 peticiones por hora por cada IP
    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(3) // Capacidad total del cubo
                .refillIntervally(3, Duration.ofHours(1)) // Tiempo asignado para reiniciar el cubo
                .build();
                
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Aplicamos el filtro a la ruta del formulario de contacto
        if (httpRequest.getRequestURI().startsWith("/api/contacto")) {
            String ip = httpRequest.getRemoteAddr(); // Obtenemos la IP del cliente
            Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

            if (bucket.tryConsume(1)) {
                // Si hay token disponible, la petición continúa
                chain.doFilter(request, response);
            } else {
                // Si no hay token, devolvemos un error 429 "Too Many Requests"
                httpResponse.setStatus(429);
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\": \"Demasiadas peticiones\", \"mensaje\": \"Límite de envíos excedido. Intenta más tarde.\"}");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}