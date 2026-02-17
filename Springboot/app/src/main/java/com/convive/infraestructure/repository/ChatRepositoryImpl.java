package com.convive.infrastructure.repository;

import com.convive.domain.model.Chat;
import com.convive.domain.repository.ChatRepository;
import com.convive.infrastructure.repository.jpa.ChatJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository jpaRepository;

    public ChatRepositoryImpl(ChatJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Chat> findByUserId(Long userId) {
        return jpaRepository.findByUsuario1IdOrUsuario2Id(userId, userId);
    }

    @Override
    public Chat save(Chat chat) {
        return jpaRepository.save(chat);
    }
}