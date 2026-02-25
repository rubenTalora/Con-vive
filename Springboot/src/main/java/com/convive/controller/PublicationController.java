package com.convive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import com.convive.entity.Publication;
import com.convive.repository.PublicationRepository;
import com.convive.service.OdooSubscriptionService;

@RestController
@RequestMapping("/api/publications") // Ruta para todas las publicaciones
@RequiredArgsConstructor
public class PublicationController {

    // Inyección automática del repository
    private final PublicationRepository repository;
    private final OdooSubscriptionService odooSubscriptionService;


    // OBTENER TODAS LAS PUBLICACIONES

    @GetMapping
    public List<Publication> getAll() {
        return repository.findAll();
    }

    // BUSCAR PUBLICACIÓN POR ID

    @GetMapping("/{id}")
    public Publication getById(@PathVariable Long id) {
        // Si no existe lanza excepción
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
    }

    // CREAR PUBLICACIÓN — verifica cupo de suscripción en Odoo antes de guardar

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody Publication publication,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        boolean allowed = odooSubscriptionService.consumePublication(authHeader);
        if (!allowed) {
            return ResponseEntity.status(403).body(
                Map.of("error", "Límite de publicaciones de suscripción alcanzado o sin suscripción activa")
            );
        }

        // Que no sea un id manual
        publication.setId(null);

        return ResponseEntity.ok(repository.save(publication));
    }

    // ACTUALIZAR PUBLICATION

    @PutMapping("/{id}")
    public Publication update(@PathVariable Long id, 
                            @RequestBody Publication updated) {

        // Buscar la publicación existente
        Publication pub = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Actualizamos los campos que queremos modificar
        pub.setTitle(updated.getTitle());
        pub.setDescription(updated.getDescription());
        pub.setType(updated.getType());
        pub.setPrice(updated.getPrice());
        pub.setCity(updated.getCity());
        pub.setBaños(updated.getBaños());
        pub.setHabitaciones(updated.getHabitaciones());
        pub.setMetros(updated.getMetros());
        

        // Guardar cambios
        return repository.save(pub);
    }

    // BORRAR PUBLICACIÓN

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        // Verificamos que exista antes de borrar
        if (!repository.existsById(id)) {
            throw new RuntimeException("Publicación no encontrada");
        }

        repository.deleteById(id);
    }
}
