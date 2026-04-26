package com.contactForm.services;

import com.contactForm.dto.LibroDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadingTrackerService {

    @Value("${notion.api.token}")
    private String notionToken;

    @Value("${notion.database.id}")
    private String databaseId;

    @Value("${notion.api.version}")
    private String notionVersion;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<LibroDTO> obtenerLibrosEnLectura() {
        String url = "https://api.notion.com/v1/databases/" + databaseId + "/query";

        // Configuramos los Headers de Notion
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(notionToken);
        headers.set("Notion-Version", notionVersion);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Traemos los libros que tenga el Status "Reading"
        String body = "{ \"filter\": { \"property\": \"Status\", \"status\": { \"equals\": \"Reading\" } } }";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return mapearRespuestaANotion(response.getBody());
        } catch (Exception e) {
            System.err.println("Error al consultar Notion: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<LibroDTO> mapearRespuestaANotion(String jsonResponse) {
        List<LibroDTO> libros = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            for (JsonNode node : results) {
                LibroDTO libro = new LibroDTO();
                JsonNode props = node.path("properties");

                // Extraemos los datos del JSON de Notion
                libro.setTitulo(props.path("Title").path("title").get(0).path("plain_text").asText());
                libro.setAutor(props.path("Author").path("rich_text").get(0).path("plain_text").asText());
                libro.setGenero(props.path("Literary Genre").path("multi_select").get(0).path("name").asText());
                libro.setProgreso(props.path("Progress").path("number").asDouble());
                libro.setEstado(props.path("Status").path("status").path("name").asText());
                
                // Obtenemos el URL de la portada del libro
                libro.setPortadaUrl(node.path("cover").path("external").path("url").asText());

                libros.add(libro);
            }
        } catch (Exception e) {
            System.err.println("Error al parsear JSON de Notion: " + e.getMessage());
        }
        return libros;
    }
}