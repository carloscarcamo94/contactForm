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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(notionToken);
        headers.set("Notion-Version", notionVersion);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //String body = "{ \"filter\": { \"property\": \"Status\", \"status\": { \"equals\": \"Reading\" } } }";
        
        String body = "{}"; // Le decimos a Notion: "Tráeme todo sin filtrar"

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            System.out.println(">>> JSON obtenido de Notion: " + response.getBody());
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

                // 1. Name (Tipo: Title)
                if (props.path("Name").path("title").isArray() && props.path("Name").path("title").size() > 0) {
                    libro.setTitulo(props.path("Name").path("title").get(0).path("plain_text").asText());
                }

                // 2. Author (Tipo: Text)
                if (props.path("Author").path("rich_text").isArray() && props.path("Author").path("rich_text").size() > 0) {
                    libro.setAutor(props.path("Author").path("rich_text").get(0).path("plain_text").asText());
                }

                // 3. Literary Genre (Tipo: Select)
                if (!props.path("Literary Genre").path("select").isNull() && !props.path("Literary Genre").path("select").isMissingNode()) {
                    libro.setGenero(props.path("Literary Genre").path("select").path("name").asText());
                }

                // 4. Progress (Tipo: Formula -> Number)
                if (!props.path("Progress").path("formula").path("number").isNull()) {
                    libro.setProgreso(props.path("Progress").path("formula").path("number").asDouble());
                }

                // 5. Status (Tipo: Status)
                if (!props.path("Status").path("status").isNull() && !props.path("Status").path("status").isMissingNode()) {
                    libro.setEstado(props.path("Status").path("status").path("name").asText());
                }

                // 6. Rating (Tipo: Select)
                if (!props.path("Rating").path("select").isNull() && !props.path("Rating").path("select").isMissingNode()) {
                    libro.setRating(props.path("Rating").path("select").path("name").asText());
                }

                // 7. Cover (Portada externa)
                if (!node.path("cover").isNull() && !node.path("cover").path("external").isMissingNode()) {
                    libro.setPortadaUrl(node.path("cover").path("external").path("url").asText());
                }

                libros.add(libro);
            }
        } catch (Exception e) {
            System.err.println("Error crítico al mapear el JSON de Notion: " + e.getMessage());
        }
        return libros;
    }
}