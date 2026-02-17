package com.convive.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convive.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatId(Long chatId);
}
