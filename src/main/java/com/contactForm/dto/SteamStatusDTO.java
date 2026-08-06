package com.contactForm.dto;

public class SteamStatusDTO {
    private String username;
    private String avatarUrl;
    private String status;
    private String currentGame;
    private String playingGameName;
    private String playingGameId;

    public SteamStatusDTO() {
    }

    public SteamStatusDTO(String username, String avatarUrl, String status, String currentGame, String playingGameName, String playingGameId) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.status = status;
        this.currentGame = currentGame;
        this.playingGameName = playingGameName;
        this.playingGameId = playingGameId;
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

	public String getPlayingGameName() {
		return playingGameName;
	}

	public void setPlayingGameName(String playingGameName) {
		this.playingGameName = playingGameName;
	}

	public String getPlayingGameId() {
		return playingGameId;
	}

	public void setPlayingGameId(String playingGameId) {
		this.playingGameId = playingGameId;
	}
}