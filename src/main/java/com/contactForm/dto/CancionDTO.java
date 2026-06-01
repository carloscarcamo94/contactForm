package com.contactForm.dto;

public class CancionDTO {
    private String titulo;
    private String autor;
    private String album;
    private String portadaUrl;
    private String spotifyUrl;
    private boolean escuchandoAhora;

    public CancionDTO() {}

    public CancionDTO(String titulo, String autor, String album, String portadaUrl, String spotifyUrl, boolean escuchandoAhora) {
        this.titulo = titulo;
        this.autor = autor;
        this.album = album;
        this.portadaUrl = portadaUrl;
        this.spotifyUrl = spotifyUrl;
        this.escuchandoAhora = escuchandoAhora;
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPortadaUrl() {
		return portadaUrl;
	}

	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}

	public String getSpotifyUrl() {
		return spotifyUrl;
	}

	public void setSpotifyUrl(String spotifyUrl) {
		this.spotifyUrl = spotifyUrl;
	}

	public boolean isEscuchandoAhora() {
		return escuchandoAhora;
	}

	public void setEscuchandoAhora(boolean escuchandoAhora) {
		this.escuchandoAhora = escuchandoAhora;
	}

}