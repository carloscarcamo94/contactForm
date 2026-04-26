package com.contactForm.dto;

public class LibroDTO {
    private String titulo;
    private String autor;
    private String portadaUrl;
    private String genero;
    private Double progreso;
    private String estado;
    private Integer paginasTotales;
    private Integer paginasLeidas;
    private String comentarios;
    
    
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
	public Integer getPaginasTotales() {
		return paginasTotales;
	}
	public void setPaginasTotales(Integer paginasTotales) {
		this.paginasTotales = paginasTotales;
	}
	public Integer getPaginasLeidas() {
		return paginasLeidas;
	}
	public void setPaginasLeidas(Integer paginasLeidas) {
		this.paginasLeidas = paginasLeidas;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
    
}