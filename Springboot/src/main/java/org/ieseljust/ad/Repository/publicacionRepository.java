package org.ieseljust.ad.Repository;

import jakarta.transaction.Transactional;
import org.ieseljust.ad.Model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PublicacionRepository extends JpaRepository<Publicacion, Long>{
    public List<Publicacion> findByLocalitatContainsIgnoreCase(String nom);
}
