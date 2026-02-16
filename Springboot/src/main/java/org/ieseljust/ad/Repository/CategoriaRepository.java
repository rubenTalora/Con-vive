package org.ieseljust.ad.Repository;

import jakarta.transaction.Transactional;
import org.ieseljust.ad.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    public Optional<Categoria> findByNom(String nom);
}
