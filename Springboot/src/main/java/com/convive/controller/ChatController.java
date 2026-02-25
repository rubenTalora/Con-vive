package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;
import com.convive.entity.ChatMensaje;
import com.convive.repository.ChatMensajeRepository;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMensajeRepository repository;

    // ver mensajes solo de ese usuario
    @GetMapping("/mis-mensajes")
    public List<ChatMensaje> misMensajes(Authentication authentication) {

        String email = authentication.getName();

        return repository.findByEmail(email);
    }

    //mensajes de un chat por id
    @GetMapping("/{id}/messages")
    public List<ChatMensaje> getMessages(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();

        return repository.findByChatIdAndEmail(id, email);
    }

    // Enviar mensaje
    @PostMapping("/send")
    public ChatMensaje send(@RequestBody ChatMensaje message, Authentication authentication) {

        String email = authentication.getName();

        message.setEmail(email);

        ChatMensaje lastMessage =
                repository.findTopByChatIdOrderByMessageNumberDesc(message.getChatId());

        Long nextNumber = 1L;

        if (lastMessage != null) {
            nextNumber = lastMessage.getMessageNumber() + 1;
        }

        message.setMessageNumber(nextNumber);
        message.setTimestamp(LocalDateTime.now());

        return repository.save(message);
    }
}