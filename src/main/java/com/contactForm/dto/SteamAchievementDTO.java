package com.contactForm.dto;

public class SteamAchievementDTO {
    private String appId;
    private String gameName;
    private String bannerUrl;
    private Integer unlockedAchievements;
    private Integer totalAchievements;
    private Double completionPercentage;

    public SteamAchievementDTO() {
    }

    public SteamAchievementDTO(String appId, String gameName, String bannerUrl, Integer unlockedAchievements, Integer totalAchievements, Double completionPercentage) {
        this.appId = appId;
        this.gameName = gameName;
        this.bannerUrl = bannerUrl;
        this.unlockedAchievements = unlockedAchievements;
        this.totalAchievements = totalAchievements;
        this.completionPercentage = completionPercentage;
    }

    // Getters y Setters
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getUnlockedAchievements() {
        return unlockedAchievements;
    }

    public void setUnlockedAchievements(Integer unlockedAchievements) {
        this.unlockedAchievements = unlockedAchievements;
    }

    public Integer getTotalAchievements() {
        return totalAchievements;
    }

    public void setTotalAchievements(Integer totalAchievements) {
        this.totalAchievements = totalAchievements;
    }

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}