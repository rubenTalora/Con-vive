package com.convive.application.publicacion;

import com.convive.domain.model.Publicacion;
import com.convive.domain.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePublicacionUseCase {

    private final PublicacionRepository repository;

    public CreatePublicacionUseCase(PublicacionRepository repository) {
        this.repository = repository;
    }

    public Publicacion execute(Publicacion publicacion) {
        return repository.save(publicacion);
    }
}