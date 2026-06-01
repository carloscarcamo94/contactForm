package com.contactForm.services;

import com.contactForm.dto.CancionDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.refresh.token}")
    private String refreshToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Obtemos un Access Token temporal usando el Refresh Token permanente
    private String obtenerAccessToken() {
        String url = "https://accounts.spotify.com/api/token";
        
        HttpHeaders headers = new HttpHeaders();
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("access_token").asText();
        } catch (Exception e) {
            System.err.println("Error al renovar token de Spotify: " + e.getMessage());
            return null;
        }
    }

    public CancionDTO obtenerTrackActual() {
        String accessToken = obtenerAccessToken();
        if (accessToken == null) return null;

        // Intentar obtener la canción que suena AHORA
        String urlCurrentlyPlaying = "https://api.spotify.com/v1/me/player/currently-playing";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(urlCurrentlyPlaying, HttpMethod.GET, entity, String.class);

            // HTTP 204 o cuerpo vacío significa que el usuario no está escuchando música
            if (response.getStatusCode() == HttpStatus.NO_CONTENT || response.getBody() == null) {
                return obtenerUltimaEscuchada(accessToken);
            }

            JsonNode root = objectMapper.readTree(response.getBody());
            // Si está en modo comerciales o podcast de audio vacío, pasamos a las recientes
            if (root.path("item").isNull()) return obtenerUltimaEscuchada(accessToken);

            return mapearCancion(root.path("item"), true);

        } catch (Exception e) {
            System.err.println("Error obteniendo reproducción actual, recurriendo a historial: " + e.getMessage());
            return obtenerUltimaEscuchada(accessToken);
        }
    }

    // Fallback: Traemos la última canción reproducida si el reproductor está apagado
    private CancionDTO obtenerUltimaEscuchada(String accessToken) {
        String urlRecentlyPlayed = "https://api.spotify.com/v1/me/player/recently-played?limit=1";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(urlRecentlyPlayed, HttpMethod.GET, entity, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode items = root.path("items");

            if (items.isArray() && !items.isEmpty()) {
                JsonNode trackNode = items.get(0).path("track");
                return mapearCancion(trackNode, false);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo historial de Spotify: " + e.getMessage());
        }
        return null;
    }

    private CancionDTO mapearCancion(JsonNode itemNode, boolean enVivo) {
        CancionDTO cancion = new CancionDTO();
        cancion.setTitulo(itemNode.path("name").asText("Track Desconocido"));
        cancion.setSpotifyUrl(itemNode.path("external_urls").path("spotify").asText("#"));
        cancion.setEscuchandoAhora(enVivo);

        // Artistas
        JsonNode artistas = itemNode.path("artists");
        if (artistas.isArray() && !artistas.isEmpty()) {
            StringBuilder nombres = new StringBuilder();
            for (int i = 0; i < artistas.size(); i++) {
                nombres.append(artistas.get(i).path("name").asText());
                if (i < artistas.size() - 1) nombres.append(", ");
            }
            cancion.setAutor(nombres.toString());
        } else {
            cancion.setAutor("Artista Desconocido");
        }

        // Álbum e imágenes de portada
        JsonNode albumNode = itemNode.path("album");
        cancion.setAlbum(albumNode.path("name").asText("Álbum Desconocido"));
        
        JsonNode imagenes = albumNode.path("images");
        if (imagenes.isArray() && !imagenes.isEmpty()) {
            // Usamos Index 1 que es la resolución mediana (300x300), ideal para web
            cancion.setPortadaUrl(imagenes.get(1).path("url").asText());
        }

        return cancion;
    }
}