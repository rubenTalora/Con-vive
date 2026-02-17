package com.convive.domain.repository;

import com.convive.domain.model.Mensaje;
import java.util.List;

public interface MensajeRepository {

    List<Mensaje> findByChatId(Long chatId);
    Mensaje save(Mensaje mensaje);
}