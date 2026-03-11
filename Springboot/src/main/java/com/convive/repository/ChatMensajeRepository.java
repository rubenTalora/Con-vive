package com.convive.repository;

import com.convive.entity.ChatMensaje;
import com.convive.entity.ChatMensajeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatMensajeRepository extends JpaRepository<ChatMensaje, ChatMensajeId> {

    List<ChatMensaje> findBySenderOrReceiver(String sender, String receiver);

    @Query("SELECT m FROM ChatMensaje m WHERE m.chatId = :chatId AND (m.sender = :email OR m.receiver = :email)")
    List<ChatMensaje> findByChatIdAndParticipant(@Param("chatId") Long chatId, @Param("email") String email);

    ChatMensaje findTopByChatIdOrderByMessageNumberDesc(Long chatId);
}