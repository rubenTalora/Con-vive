package com.convive.application.publicacion;

import com.convive.domain.model.Publicacion;
import com.convive.domain.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdatePublicacionUseCase {

    private final PublicacionRepository repository;

    public UpdatePublicacionUseCase(PublicacionRepository repository) {
        this.repository = repository;
    }

    public Publicacion execute(Long id, Publicacion updated) {
        Publicacion existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada"));

        existing.setTitulo(updated.getTitulo());
        existing.setDescripcion(updated.getDescripcion());
        existing.setPrecio(updated.getPrecio());
        existing.setTipo(updated.getTipo());
        existing.setCiudad(updated.getCiudad());

        return repository.save(existing);
    }
}