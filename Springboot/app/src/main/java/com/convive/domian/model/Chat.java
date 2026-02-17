package com.convive.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuario1Id;
    private Long usuario2Id;

    public Chat() {}

    public Chat(Long usuario1Id, Long usuario2Id) {
        this.usuario1Id = usuario1Id;
        this.usuario2Id = usuario2Id;
    }

    public Long getId() { return id; }
    public Long getUsuario1Id() { return usuario1Id; }
    public Long getUsuario2Id() { return usuario2Id; }

    public void setId(Long id) { this.id = id; }
    public void setUsuario1Id(Long usuario1Id) { this.usuario1Id = usuario1Id; }
    public void setUsuario2Id(Long usuario2Id) { this.usuario2Id = usuario2Id; }
}