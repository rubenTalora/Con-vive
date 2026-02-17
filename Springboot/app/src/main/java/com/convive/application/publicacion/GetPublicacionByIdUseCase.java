package com.convive.application.publicacion;

import com.convive.domain.model.Publicacion;
import com.convive.domain.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPublicacionByIdUseCase {

    private final PublicacionRepository repository;

    public GetPublicacionByIdUseCase(PublicacionRepository repository) {
        this.repository = repository;
    }

    public Publicacion execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada"));
    }
}