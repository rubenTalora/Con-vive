package com.convive.infrastructure.repository;

import com.convive.domain.model.Mensaje;
import com.convive.domain.repository.MensajeRepository;
import com.convive.infrastructure.repository.jpa.MensajeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MensajeRepositoryImpl implements MensajeRepository {

    private final MensajeJpaRepository jpaRepository;

    public MensajeRepositoryImpl(MensajeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Mensaje> findByChatId(Long chatId) {
        return jpaRepository.findByChatId(chatId);
    }

    @Override
    public Mensaje save(Mensaje mensaje) {
        return jpaRepository.save(mensaje);
    }
}