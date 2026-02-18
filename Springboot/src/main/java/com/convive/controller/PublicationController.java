package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.convive.entity.Publication;
import com.convive.repository.PublicationRepository;

@RestController
@RequestMapping("/api/publications") // Ruta para todas las publicaciones
@RequiredArgsConstructor
public class PublicationController {

    // Inyección automática del repository
    private final PublicationRepository repository;


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

    // CREAR PUBLICATION

    @PostMapping
    public Publication create(@RequestBody Publication publication) {

        // Que no sea in id manual
        publication.setId(null);

        return repository.save(publication);
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
