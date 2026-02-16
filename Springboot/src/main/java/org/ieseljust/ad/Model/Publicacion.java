package org.ieseljust.ad.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String ciudad;
    private Double precio;
    private Integer habitacionesDisponibles;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario propietario;

    // getters y setters
}