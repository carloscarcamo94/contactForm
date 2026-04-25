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

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(3)
                .refillIntervally(3, Duration.ofHours(1))
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getRequestURI().startsWith("/api/contacto")) {
            
            String ip = httpRequest.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = httpRequest.getRemoteAddr();
            } else {
                ip = ip.split(",")[0].trim();
            }

            Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

            if (bucket.tryConsume(1)) {
                chain.doFilter(request, response);
            } else {
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