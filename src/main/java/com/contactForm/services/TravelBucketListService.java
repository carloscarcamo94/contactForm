package com.contactForm.services;

import com.contactForm.dto.DestinoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TravelBucketListService {

    @Value("${notion.api.token}")
    private String notionToken;

    @Value("${notion.travel.database.id}")
    private String databaseId;

    @Value("${notion.api.version}")
    private String notionVersion;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<DestinoDTO> obtenerDestinos() {
        String url = "https://api.notion.com/v1/databases/" + databaseId + "/query";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(notionToken);
        headers.set("Notion-Version", notionVersion);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Filtro para traer ambos estados
        String body = "{ \"filter\": { \"or\": [ " +
                      "{ \"property\": \"Status\", \"status\": { \"equals\": \"Want to go\" } }, " +
                      "{ \"property\": \"Status\", \"status\": { \"equals\": \"Visited\" } } " +
                      "] } }";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return mapearRespuesta(response.getBody());
        } catch (Exception e) {
            System.err.println("Error al consultar destinos en Notion: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<DestinoDTO> mapearRespuesta(String jsonResponse) {
        List<DestinoDTO> destinos = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            for (JsonNode node : results) {
                DestinoDTO destino = new DestinoDTO();
                JsonNode props = node.path("properties");

                // Name (Title)
                if (props.path("Name").path("title").isArray() && !props.path("Name").path("title").isEmpty()) {
                    destino.setNombre(props.path("Name").path("title").get(0).path("plain_text").asText());
                }

                // Status
                if (!props.path("Status").path("status").isMissingNode() && !props.path("Status").path("status").isNull()) {
                    destino.setEstado(props.path("Status").path("status").path("name").asText());
                }

                // Continent (Multi-select: tomamos el primero para mantener la UI limpia)
                if (props.path("Continent").path("multi_select").isArray() && !props.path("Continent").path("multi_select").isEmpty()) {
                    destino.setContinente(props.path("Continent").path("multi_select").get(0).path("name").asText());
                }

                // Budget (Multi-select)
                if (props.path("Budget").path("multi_select").isArray() && !props.path("Budget").path("multi_select").isEmpty()) {
                    destino.setPresupuesto(props.path("Budget").path("multi_select").get(0).path("name").asText());
                }

                // Duration (Multi-select)
                if (props.path("Duration").path("multi_select").isArray() && !props.path("Duration").path("multi_select").isEmpty()) {
                    destino.setDuracion(props.path("Duration").path("multi_select").get(0).path("name").asText());
                }

                // Favorite & Booked (Checkboxes)
                destino.setFavorito(props.path("Favorite").path("checkbox").asBoolean(false));
                destino.setReservado(props.path("Booked").path("checkbox").asBoolean(false));

                // Cover
                if (!node.path("cover").isNull() && !node.path("cover").path("external").isMissingNode()) {
                    destino.setPortadaUrl(node.path("cover").path("external").path("url").asText());
                }

                destinos.add(destino);
            }
        } catch (Exception e) {
            System.err.println("Error mapeando JSON de Destinos: " + e.getMessage());
        }
        return destinos;
    }
}