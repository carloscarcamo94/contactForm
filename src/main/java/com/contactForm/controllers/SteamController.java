package com.contactForm.controllers;

import com.contactForm.dto.SteamStatusDTO;
import com.contactForm.dto.SteamGameDTO;
import com.contactForm.dto.SteamAchievementDTO;
import com.contactForm.services.SteamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/steam")
public class SteamController {

    private final SteamService steamService;

    public SteamController(SteamService steamService) {
        this.steamService = steamService;
    }

    @GetMapping("/current")
    public ResponseEntity<SteamStatusDTO> getCurrentStatus() {
        SteamStatusDTO status = steamService.getCurrentStatus();
        return status != null ? ResponseEntity.ok(status) : ResponseEntity.noContent().build();
    }

    @GetMapping("/top-played")
    public ResponseEntity<List<SteamGameDTO>> getTopPlayed() {
        List<SteamGameDTO> games = steamService.getTopPlayedGames();
        return games.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(games);
    }

    @GetMapping("/achievements")
    public ResponseEntity<List<SteamAchievementDTO>> getAchievements() {
        List<SteamAchievementDTO> achievements = steamService.getSpecialGamesAchievements();
        return achievements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(achievements);
    }
}