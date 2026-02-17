package com.convive.domain.repository;

import com.convive.domain.model.Chat;
import java.util.List;

public interface ChatRepository {

    List<Chat> findByUserId(Long userId);
    Chat save(Chat chat);
}