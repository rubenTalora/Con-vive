
package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;
import com.convive.entity.ChatMensaje;
import com.convive.repository.ChatMensajeRepository;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMensajeRepository repository;

    @GetMapping("/")
    public List<ChatMensaje> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}/messages")
    public List<ChatMensaje> getMessages(@PathVariable Long id) {
        return repository.findByChatId(id);
    }

    @PostMapping("/send")
    public ChatMensaje send(@RequestBody ChatMensaje message) {
    // Buscar el ultimo mensaje de este chat
    ChatMensaje lastMessage =repository.findTopByChatIdOrderByMessageNumberDesc(message.getChatId());
    Long nextNumber = 1L;

    // Obtenemos el numero del ultimo mensaje guardado y le sumamos 1 para generar el siguiente número
    if (lastMessage != null) {
    nextNumber = lastMessage.getMessageNumber() + 1;
    }

    message.setMessageNumber(nextNumber);
    message.setTimestamp(LocalDateTime.now());

    return repository.save(message);
        }
}
