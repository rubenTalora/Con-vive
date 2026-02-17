package com.convive.domain.repository;

import com.convive.domain.model.Publicacion;
import java.util.List;
import java.util.Optional;

public interface PublicacionRepository {

    List<Publicacion> findAll();
    Optional<Publicacion> findById(Long id);
    Publicacion save(Publicacion publicacion);
    void deleteById(Long id);
}