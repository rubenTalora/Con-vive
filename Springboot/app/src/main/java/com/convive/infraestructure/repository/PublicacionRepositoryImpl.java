package com.convive.infrastructure.repository;

import com.convive.domain.model.Publicacion;
import com.convive.domain.repository.PublicacionRepository;
import com.convive.infrastructure.repository.jpa.PublicacionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PublicacionRepositoryImpl implements PublicacionRepository {

    private final PublicacionJpaRepository jpaRepository;

    public PublicacionRepositoryImpl(PublicacionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Publicacion> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Publicacion> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Publicacion save(Publicacion publicacion) {
        return jpaRepository.save(publicacion);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}