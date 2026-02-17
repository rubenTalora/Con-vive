package com.convive.application.publicacion;

import com.convive.domain.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class DeletePublicacionUseCase {

    private final PublicacionRepository repository;

    public DeletePublicacionUseCase(PublicacionRepository repository) {
        this.repository = repository;
    }

    public void execute(Long id) {
        repository.deleteById(id);
    }
}