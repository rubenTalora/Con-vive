package com.convive.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private Long emisorId;
    private String contenido;
    private LocalDateTime fecha;

    public Mensaje() {}

    public Mensaje(Long chatId, Long emisorId, String contenido, LocalDateTime fecha) {
        this.chatId = chatId;
        this.emisorId = emisorId;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public Long getChatId() { return chatId; }
    public Long getEmisorId() { return emisorId; }
    public String getContenido() { return contenido; }
    public LocalDateTime getFecha() { return fecha; }

    public void setId(Long id) { this.id = id; }
    public void setChatId(Long chatId) { this.chatId = chatId; }
    public void setEmisorId(Long emisorId) { this.emisorId = emisorId; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}