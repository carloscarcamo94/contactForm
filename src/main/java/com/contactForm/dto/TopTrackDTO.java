package com.contactForm.dto;

public class TopTrackDTO {
    private String titulo;
    private String artista;
    private String album;
    private String portadaUrl;
    private String spotifyUrl;

    // Constructor vacío obligatorio para la deserialización de Jackson
    public TopTrackDTO() {}

    // Constructor parametrizado por conveniencia con el servicio
    public TopTrackDTO(String titulo, String artista, String album, String portadaUrl, String spotifyUrl) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.portadaUrl = portadaUrl;
        this.spotifyUrl = spotifyUrl;
    }

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
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

}
