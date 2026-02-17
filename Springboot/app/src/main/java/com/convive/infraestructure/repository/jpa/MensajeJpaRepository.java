package com.convive.infrastructure.repository.jpa;

import com.convive.domain.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeJpaRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByChatId(Long chatId);
}