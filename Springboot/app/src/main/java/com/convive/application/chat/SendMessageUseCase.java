package com.convive.application.chat;

import com.convive.domain.model.Mensaje;
import com.convive.domain.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SendMessageUseCase {

    private final MensajeRepository repository;

    public SendMessageUseCase(MensajeRepository repository) {
        this.repository = repository;
    }

    public Mensaje execute(Mensaje mensaje) {
        mensaje.setFecha(LocalDateTime.now());
        return repository.save(mensaje);
    }
}