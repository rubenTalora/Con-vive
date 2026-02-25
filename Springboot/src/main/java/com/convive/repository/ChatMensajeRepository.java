package com.convive.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convive.entity.ChatMensaje;
import com.convive.entity.ChatMensajeId;

public interface ChatMensajeRepository extends JpaRepository<ChatMensaje, ChatMensajeId> {
    List<ChatMensaje> findByChatId(Long chatId);

    //Para saber cual es el ultimo mensaje del chat
    ChatMensaje findTopByChatIdOrderByMessageNumberDesc(Long chatId);
    List<ChatMensaje> findByEmail(String email);
    List<ChatMensaje> findByChatIdAndEmail(Long chatId, String email);
}
