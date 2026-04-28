package com.contactForm.dto;

public class LibroDTO {
    private String titulo;
    private String autor;
    private String portadaUrl;
    private String genero;
    private Double progreso;
    private String estado;
    private String rating;
    
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
	public String getPortadaUrl() {
		return portadaUrl;
	}
	public void setPortadaUrl(String portadaUrl) {
		this.portadaUrl = portadaUrl;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Double getProgreso() {
		return progreso;
	}
	public void setProgreso(Double progreso) {
		this.progreso = progreso;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
    
}