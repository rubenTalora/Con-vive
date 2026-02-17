package com.convive.infrastructure.repository.jpa;

import com.convive.domain.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUsuario1IdOrUsuario2Id(Long usuario1Id, Long usuario2Id);
}