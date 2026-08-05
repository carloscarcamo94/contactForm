package com.contactForm.dto;

public class SteamStatusDTO {
    private String username;
    private String avatarUrl;
    private String status;
    private String currentGame;

    public SteamStatusDTO() {
    }

    public SteamStatusDTO(String username, String avatarUrl, String status, String currentGame) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.currentGame = currentGame;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }
}