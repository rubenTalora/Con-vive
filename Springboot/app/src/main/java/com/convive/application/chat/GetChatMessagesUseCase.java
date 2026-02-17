package com.convive.application.chat;

import com.convive.domain.model.Mensaje;
import com.convive.domain.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetChatMessagesUseCase {

    private final MensajeRepository repository;

    public GetChatMessagesUseCase(MensajeRepository repository) {
        this.repository = repository;
    }

    public List<Mensaje> execute(Long chatId) {
        return repository.findByChatId(chatId);
    }
}