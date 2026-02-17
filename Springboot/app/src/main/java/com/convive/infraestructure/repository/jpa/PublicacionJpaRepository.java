package com.convive.infrastructure.repository.jpa;

import com.convive.domain.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionJpaRepository extends JpaRepository<Publicacion, Long> {
}