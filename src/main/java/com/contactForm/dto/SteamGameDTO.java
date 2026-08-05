package com.contactForm.dto;

public class SteamGameDTO {
    private String appId;
    private String name;
    private String bannerUrl;
    private Integer playTimeHours;

    public SteamGameDTO() {
    }

    public SteamGameDTO(String appId, String name, String bannerUrl, Integer playTimeHours) {
        this.appId = appId;
        this.name = name;
        this.bannerUrl = bannerUrl;
        this.playTimeHours = playTimeHours;
    }

    // Getters y Setters
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Integer getPlayTimeHours() {
        return playTimeHours;
    }

    public void setPlayTimeHours(Integer playTimeHours) {
        this.playTimeHours = playTimeHours;
    }
}