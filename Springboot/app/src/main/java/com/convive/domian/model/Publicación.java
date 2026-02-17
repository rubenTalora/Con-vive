package com.convive.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private Double precio;
    private String tipo; // ALQUILER, VENTA, COMPARTIR
    private String ciudad;
    private Long propietarioId;

    public Publicacion() {}

    public Publicacion(String titulo, String descripcion, Double precio, String tipo, String ciudad, Long propietarioId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.ciudad = ciudad;
        this.propietarioId = propietarioId;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Double getPrecio() { return precio; }
    public String getTipo() { return tipo; }
    public String getCiudad() { return ciudad; }
    public Long getPropietarioId() { return propietarioId; }

    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setPropietarioId(Long propietarioId) { this.propietarioId = propietarioId; }
}