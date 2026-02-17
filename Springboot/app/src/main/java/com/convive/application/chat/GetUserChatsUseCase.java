package com.convive.application.chat;

import com.convive.domain.model.Chat;
import com.convive.domain.repository.ChatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetUserChatsUseCase {

    private final ChatRepository repository;

    public GetUserChatsUseCase(ChatRepository repository) {
        this.repository = repository;
    }

    public List<Chat> execute(Long userId) {
        return repository.findByUserId(userId);
    }
}