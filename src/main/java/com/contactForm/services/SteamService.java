package com.contactForm.services;

import com.contactForm.dto.SteamStatusDTO;
import com.contactForm.dto.SteamGameDTO;
import com.contactForm.dto.SteamAchievementDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class SteamService {

    @Value("${steam.api.key}")
    private String apiKey;

    @Value("${steam.user.id}")
    private String steamId;

    @Value("${steam.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SteamService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Estado del User (HUD)
    public SteamStatusDTO getCurrentStatus() {
        String url = String.format("%s/ISteamUser/GetPlayerSummaries/v0002/?key=%s&steamids=%s", baseUrl, apiKey, steamId);
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode player = root.path("response").path("players").get(0);

            String username = player.path("personaname").asText();
            String avatarUrl = player.path("avatarfull").asText();
            int personastate = player.path("personastate").asInt();
            
            // Lógica de estado y juego actual
            String currentGame = player.has("gameextrainfo") ? player.path("gameextrainfo").asText() : null;
            String status = determineStatus(personastate, currentGame);

            return new SteamStatusDTO(username, avatarUrl, status, currentGame);
        } catch (Exception e) {
            return null; 
        }
    }

    private String determineStatus(int stateCode, String currentGame) {
        if (currentGame != null) return "In-Game";
        return switch (stateCode) {
            case 1 -> "Online";
            case 3 -> "Away";
            case 4 -> "Snooze";
            default -> "Offline";
        };
    }

    // Top de juegos más jugados
    public List<SteamGameDTO> getTopPlayedGames() {
        String url = String.format("%s/IPlayerService/GetOwnedGames/v0001/?key=%s&steamid=%s&include_appinfo=true", baseUrl, apiKey, steamId);
        
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode gamesNode = objectMapper.readTree(response).path("response").path("games");
            
            List<SteamGameDTO> allGames = new ArrayList<>();
            for (JsonNode game : gamesNode) {
                String appId = game.path("appid").asText();
                String name = game.path("name").asText();
                int playtimeMinutes = game.path("playtime_forever").asInt();
                
                String bannerUrl = String.format("https://cdn.cloudflare.steamstatic.com/steam/apps/%s/capsule_616x353.jpg", appId);
                
                // Pasamos las horas calculadas como los minutos reales para ordenar
                allGames.add(new SteamGameDTO(appId, name, bannerUrl, playtimeMinutes / 60, playtimeMinutes));
            }

            // Ordenamos por los minutos jugados
            allGames.sort((a, b) -> Integer.compare(b.getPlayTimeMinutes(), a.getPlayTimeMinutes()));
            return allGames.subList(0, Math.min(6, allGames.size()));

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Obtenemos los logros obtenidos
    public List<SteamAchievementDTO> getSpecialGamesAchievements() {
        List<String> specialAppIds = List.of("367520", "1030300", "774361", "588650", "683320", "250900", "304430", "48000"); 
        List<SteamAchievementDTO> achievementsList = new ArrayList<>();

        for (String appId : specialAppIds) {
            String url = String.format("%s/ISteamUserStats/GetPlayerAchievements/v0001/?appid=%s&key=%s&steamid=%s", baseUrl, appId, apiKey, steamId);
            
            try {
                String response = restTemplate.getForObject(url, String.class);
                JsonNode stats = objectMapper.readTree(response).path("playerstats");
                
                if (!stats.path("success").asBoolean()) continue;

                String gameName = stats.path("gameName").asText();
                String bannerUrl = String.format("https://cdn.cloudflare.steamstatic.com/steam/apps/%s/capsule_616x353.jpg", appId);
                
                JsonNode achievementsNode = stats.path("achievements");
                int total = achievementsNode.size();
                int unlocked = 0;
                
                for (JsonNode ach : achievementsNode) {
                    if (ach.path("achieved").asInt() == 1) unlocked++;
                }
                
                double percentage = total > 0 ? ((double) unlocked / total) * 100 : 0.0;
                achievementsList.add(new SteamAchievementDTO(appId, gameName, bannerUrl, unlocked, total, percentage));
                
            } catch (Exception e) {
                // Ignorar si el juego no tiene logros o hubo un error
            }
        }
        return achievementsList;
    }
}